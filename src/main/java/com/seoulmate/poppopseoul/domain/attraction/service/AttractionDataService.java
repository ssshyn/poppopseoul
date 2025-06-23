package com.seoulmate.poppopseoul.domain.attraction.service;

import com.seoulmate.poppopseoul.domain.attraction.entity.AttractionId;
import com.seoulmate.poppopseoul.domain.attraction.entity.AttractionInfo;
import com.seoulmate.poppopseoul.domain.attraction.enumeration.AttractionDetailCode;
import com.seoulmate.poppopseoul.domain.attraction.feign.SeoulApiCode;
import com.seoulmate.poppopseoul.domain.attraction.feign.SeoulFeign;
import com.seoulmate.poppopseoul.domain.attraction.feign.SeoulFeignResponse;
import com.seoulmate.poppopseoul.domain.attraction.feign.dto.MountainParkResponse;
import com.seoulmate.poppopseoul.domain.attraction.repository.AttractionIdRepository;
import com.seoulmate.poppopseoul.domain.attraction.repository.AttractionInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
     * 산과 공원 데이터 수집
     */
    public void insertMountainPark() {
        // 산과 공원 API 호출
        SeoulApiCode mountainCode = SeoulApiCode.MOUNTAIN_PARK;
        SeoulFeignResponse<MountainParkResponse> mountainParkResponse =
                (SeoulFeignResponse<MountainParkResponse>) seoulFeign.getSeoulData(mountainCode.getApiCode(), mountainCode.getStartIndex(), mountainCode.getEndIndex());


        // response 받아오기
        List<MountainParkResponse> mountains = mountainParkResponse.getMountainParkFeignResponse().getRow();
        mountains.forEach(
                mountain -> {
                    AttractionId attractionId = attractionIdRepository.save(
                            new AttractionId(Collections.singletonList(AttractionDetailCode.PARK)));

                    attractionInfoRepository.save(AttractionInfo.ofMountain(mountain));
                }
        );
    }
}
