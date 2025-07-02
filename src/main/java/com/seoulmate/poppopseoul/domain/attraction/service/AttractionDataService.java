package com.seoulmate.poppopseoul.domain.attraction.service;

import com.seoulmate.poppopseoul.domain.attraction.entity.AttractionId;
import com.seoulmate.poppopseoul.domain.attraction.entity.AttractionInfo;
import com.seoulmate.poppopseoul.domain.attraction.enumeration.AttractionDetailCode;
import com.seoulmate.poppopseoul.domain.attraction.feign.SeoulApiCode;
import com.seoulmate.poppopseoul.domain.attraction.feign.SeoulFeign;
import com.seoulmate.poppopseoul.domain.attraction.feign.SeoulFeignResponse;
import com.seoulmate.poppopseoul.domain.attraction.feign.dto.MountainParkResponse;
import com.seoulmate.poppopseoul.domain.attraction.feign.dto.TourSpotResponse;
import com.seoulmate.poppopseoul.domain.attraction.repository.AttractionIdRepository;
import com.seoulmate.poppopseoul.domain.attraction.repository.AttractionInfoRepository;
import com.seoulmate.poppopseoul.exception.ErrorCode;
import com.seoulmate.poppopseoul.exception.ErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttractionDataService {
    private final AttractionIdRepository attractionIdRepository;
    private final AttractionInfoRepository attractionInfoRepository;
    private final SeoulFeign seoulFeign;

    /**
     * 서울 공공 데이터 API 가져와서 저장
     */
    public void saveSeoulAttractionData() throws Exception {
        // 산과 공원 데이터 수집
        List<MountainParkResponse> mountainParkItems = fetchMountainData();
        List<AttractionInfo> attractionInfos = new ArrayList<>(processAttractionItems(mountainParkItems));

        // 관광명소 데이터 수집
        List<TourSpotResponse> tourSpotItems = fetchTourSpotData();
        attractionInfos.addAll(processAttractionItems(tourSpotItems));

        // 엔티티 저장
        attractionInfoRepository.saveAll(attractionInfos);
    }

    /**
     * 관광지 아이템 List > AttractionInfo 엔티티 List 생성
     */
    public <T> List<AttractionInfo> processAttractionItems(List<T> attractionItems) throws Exception{
        List<AttractionInfo> attractionInfoEntities = new ArrayList<>();
        List<AttractionDetailCode> detailCodes = List.of(AttractionDetailCode.values());

        try {
            for (T item : attractionItems) {
                AttractionInfo attractionInfo = createAttractionInfo(item, detailCodes);
                attractionInfoEntities.add(attractionInfo);
            }
        } catch (Exception e) {
            throw new ErrorException(ErrorCode.DATA_PROCESS_ERROR);
        }

        return attractionInfoEntities;
    }

    /**
     * 관광지 아이템 > Attraction 변환
     */
    private <T> AttractionInfo createAttractionInfo(T item, List<AttractionDetailCode> detailCodes) {
        List<AttractionDetailCode> attractionCodes;

        if(item.getClass() == TourSpotResponse.class) {
            // 관광명소 처리의 경우, attractionCode 판별 로직 타야 함
            attractionCodes = classifyAttraction((TourSpotResponse)item, detailCodes);
        } else {
            // 아닌 경우 산과 공원
            // todo: 타입 추가 시 분기 처리 필요
            attractionCodes = Collections.singletonList(AttractionDetailCode.PARK);
        }

        AttractionId attractionIdEntity = attractionIdRepository.save(new AttractionId(attractionCodes));

        return AttractionInfo.saveApiInfo(item, attractionIdEntity);
    }

    /**
     * 관광명소 데이터 수집
     */
    public List<TourSpotResponse> fetchTourSpotData() throws Exception {
        List<TourSpotResponse> tourSpotItems = new ArrayList<>();
        SeoulApiCode tourSpotCode = SeoulApiCode.TOUR_SPOT;
        Integer startIndex = tourSpotCode.getStartIndex();
        Integer endIndex = tourSpotCode.getEndIndex();
        int lastIndex;

        try {
            do {
                SeoulFeignResponse<TourSpotResponse> seoulDataResponse =
                        (SeoulFeignResponse<TourSpotResponse>) seoulFeign.getSeoulData(tourSpotCode.getApiCode(), startIndex, endIndex);

                Integer totalCount = seoulDataResponse.getTourSpotResponse().getListTotalCount();
                lastIndex = totalCount + 1000;

                tourSpotItems.addAll(
                        seoulDataResponse.getTourSpotResponse().getRow().stream()
                                .filter(x -> StringUtils.equals(x.getLanguageCode(), "ko"))
                                .toList()
                );

                startIndex += 1000;
                endIndex += 1000;
            } while (lastIndex > endIndex);
        } catch (Exception e) {
            throw new ErrorException(ErrorCode.SEOUL_API_ERROR);
        }

        return tourSpotItems;
    }

    /**
     * 산과 공원 데이터 수집
     */
    public List<MountainParkResponse> fetchMountainData() {
        // 산과 공원 API 호출
        SeoulApiCode mountainCode = SeoulApiCode.MOUNTAIN_PARK;
        SeoulFeignResponse<MountainParkResponse> mountainParkResponse =
                (SeoulFeignResponse<MountainParkResponse>) seoulFeign.getSeoulData(mountainCode.getApiCode(), mountainCode.getStartIndex(), mountainCode.getEndIndex());


        // response 받아오기
        return mountainParkResponse.getMountainParkResponse().getRow();
    }

    /**
     * 관광지 분류 로직
     */
    private List<AttractionDetailCode> classifyAttraction(TourSpotResponse item, List<AttractionDetailCode> detailCodes) {
        List<AttractionDetailCode> attractionCodes = new ArrayList<>();
        List<String> tagList = List.of(item.getTag().split(","));
        String attractionName = StringUtils.trimToEmpty(item.getName());

        // 태그 기반 분류
        classifyByTags(tagList, detailCodes, attractionCodes);

        // 이름 기반 분류
        classifyByName(attractionName, detailCodes, attractionCodes);

        // 예외처리
        applyExceptionRules(attractionName, tagList, attractionCodes);

        // 기본값 설정
        if (attractionCodes.isEmpty()) {
            attractionCodes.add(AttractionDetailCode.ETC);
        }

        return attractionCodes.stream().distinct().toList();
    }

    /**
     * 태그 기반 분류
     */
    private void classifyByTags(List<String> tagList, List<AttractionDetailCode> detailCodes, List<AttractionDetailCode> attractionCodes) {
        tagList.forEach(tag ->
                detailCodes.forEach(detailCode ->
                        detailCode.getTagValues().forEach(tagValue -> {
                            if (StringUtils.trimToEmpty(tag).matches(tagValue)) {
                                attractionCodes.add(detailCode);
                            }
                        })
                )
        );
    }

    /**
     * 이름 기반 분류
     */
    private void classifyByName(String attractionName, List<AttractionDetailCode> detailCodes, List<AttractionDetailCode> attractionCodes) {
        detailCodes.forEach(detailCode ->
                detailCode.getNameValues().forEach(nameValue -> {
                    if (attractionName.matches(nameValue)) {
                        attractionCodes.add(detailCode);
                    }
                })
        );
    }

    /**
     * 예외처리 규칙 적용
     */
    private void applyExceptionRules(String attractionName, List<String> tagList, List<AttractionDetailCode> attractionCodes) {
        // 상호명 예외처리
        if (StringUtils.equals(attractionName, "삼양탕")) {
            attractionCodes.add(AttractionDetailCode.CAFE);
        }
        if (StringUtils.equals(attractionName, "용산공원갤러리")) {
            attractionCodes.add(AttractionDetailCode.PARK);
        }
        if (StringUtils.contains(attractionName, "골목길")) {
            attractionCodes.add(AttractionDetailCode.ART_MUSEUM);
        }

        // 오래가게 필터링
        boolean isOldShop = tagList.contains("오래가게");
        if (isOldShop && attractionCodes.stream().noneMatch(code -> AttractionDetailCode.getOldShop().contains(code))) {
            attractionCodes.add(AttractionDetailCode.OLD_ETC);
        }
    }

    /**
     * 주소 정보 처리
     */
    private String getAddress(TourSpotResponse item) {
        String address = org.apache.commons.lang3.StringUtils.trimToEmpty(item.getAddress());
        if (address.isEmpty()) {
            address = StringUtils.trimToEmpty(item.getNewAddress());
        }
        return address;
    }
}
