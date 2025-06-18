package com.seoulmate.poppopseoul.domain.attraction.service;

import com.seoulmate.poppopseoul.common.dto.ProgressResponse;
import com.seoulmate.poppopseoul.common.enumeration.LanguageCode;
import com.seoulmate.poppopseoul.domain.attraction.dto.AttractionCreateRequest;
import com.seoulmate.poppopseoul.domain.attraction.dto.AttractionResponse;
import com.seoulmate.poppopseoul.domain.attraction.dto.AttractionUpdateRequest;
import com.seoulmate.poppopseoul.domain.attraction.entity.AttractionId;
import com.seoulmate.poppopseoul.domain.attraction.entity.AttractionInfo;
import com.seoulmate.poppopseoul.domain.attraction.repository.AttractionIdRepository;
import com.seoulmate.poppopseoul.domain.attraction.repository.AttractionInfoRepository;
import com.seoulmate.poppopseoul.exception.ErrorCode;
import com.seoulmate.poppopseoul.exception.ErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttractionService {

    private final AttractionIdRepository attractionIdRepository;
    private final AttractionInfoRepository attractionInfoRepository;

    /**
     * 관광지 상세 조회
     */
    public AttractionResponse getDetail(Long id, LanguageCode languageCode) {
        AttractionId attractionId = attractionIdRepository.findById(id).orElseThrow(() -> new ErrorException(ErrorCode.ATTRACTION_NOT_FOUND));
        AttractionInfo attractionInfo = attractionInfoRepository.findByAttractionIdAndLanguageCode(attractionId, languageCode)
                .orElseThrow(() -> new ErrorException(ErrorCode.ATTRACTION_NOT_FOUND));

        return new AttractionResponse(attractionId, attractionInfo);
    }

    /**
     * 관광지 등록
     */
    @Transactional
    public ProgressResponse<AttractionResponse> createAttraction(AttractionCreateRequest condition) {
        AttractionId attractionId = attractionIdRepository.save(condition.toEntityId());
        AttractionInfo attractionInfo = attractionInfoRepository.save(condition.toEntityInfo(attractionId));

        return new ProgressResponse<>(true, new AttractionResponse(attractionId, attractionInfo));
    }

    /**
     * 관광지 수정
     */
    @Transactional
    public ProgressResponse<AttractionResponse> updateAttraction(AttractionUpdateRequest condition) {
        AttractionId attractionId = attractionIdRepository.findById(condition.getId()).orElseThrow(() -> new ErrorException(ErrorCode.ATTRACTION_NOT_FOUND));
        AttractionInfo attractionInfo = attractionId.getAttractionInfos().stream()
                .filter(info -> Objects.equals(info.getLanguageCode(), condition.getLanguageCode()))
                .findFirst()
                .orElseGet(() -> {
                    AttractionInfo updateInfo = condition.toEntityInfo(attractionId);
                    return attractionInfoRepository.save(updateInfo);
                });

        return new ProgressResponse<>(true, new AttractionResponse(attractionId, attractionInfo));
    }

    /**
     * 관광지 삭제
     */
    @Transactional
    public ProgressResponse<Long> deleteAttraction(Long id) {
        AttractionId attractionId = attractionIdRepository.findById(id).orElseThrow(() -> new ErrorException(ErrorCode.ATTRACTION_NOT_FOUND));
        attractionIdRepository.delete(attractionId);
        return new ProgressResponse<>(true, id);
    }
}
