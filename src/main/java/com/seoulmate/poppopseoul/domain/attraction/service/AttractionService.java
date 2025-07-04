package com.seoulmate.poppopseoul.domain.attraction.service;

import com.seoulmate.poppopseoul.common.dto.ProgressResponse;
import com.seoulmate.poppopseoul.common.enumeration.LanguageCode;
import com.seoulmate.poppopseoul.common.util.UserInfoUtil;
import com.seoulmate.poppopseoul.domain.attraction.dto.AttractionCreateRequest;
import com.seoulmate.poppopseoul.domain.attraction.dto.AttractionResponse;
import com.seoulmate.poppopseoul.domain.attraction.dto.AttractionUpdateRequest;
import com.seoulmate.poppopseoul.domain.attraction.entity.AttractionId;
import com.seoulmate.poppopseoul.domain.attraction.entity.AttractionInfo;
import com.seoulmate.poppopseoul.domain.attraction.entity.AttractionLike;
import com.seoulmate.poppopseoul.domain.attraction.repository.AttractionIdRepository;
import com.seoulmate.poppopseoul.domain.attraction.repository.AttractionInfoRepository;
import com.seoulmate.poppopseoul.domain.attraction.repository.AttractionLikeRepository;
import com.seoulmate.poppopseoul.domain.user.entity.User;
import com.seoulmate.poppopseoul.domain.user.repository.UserRepository;
import com.seoulmate.poppopseoul.exception.ErrorCode;
import com.seoulmate.poppopseoul.exception.ErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttractionService {

    private final AttractionIdRepository attractionIdRepository;
    private final AttractionInfoRepository attractionInfoRepository;
    private final AttractionLikeRepository attractionLikeRepository;
    private final UserRepository userRepository;


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
        AttractionId attractionId = attractionIdRepository.save(AttractionId.toEntity(condition));
        AttractionInfo infoKor = attractionInfoRepository.save(AttractionInfo.toEntity(condition, attractionId, LanguageCode.KOR));
        AttractionInfo infoEng = attractionInfoRepository.save(AttractionInfo.toEntity(condition, attractionId, LanguageCode.ENG));

        return new ProgressResponse<>(true, new AttractionResponse(attractionId, condition.getLanguageCode().isKorean() ? infoKor : infoEng));
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

    /**
     * 관광지 좋아요 등록
     */
    @Transactional
    public ProgressResponse<Long> saveAttractionLike(Long id) {
        if(!UserInfoUtil.isAuthenticated()) {
            throw new ErrorException(ErrorCode.LOGIN_NOT_ACCESS);
        }

        // 현재 로그인 한 사용자
        User user = userRepository.findById(Objects.requireNonNull(UserInfoUtil.getUserId()))
                .orElseThrow(() -> new ErrorException(ErrorCode.USER_NOT_FOUND));

        // attraction 유효성
        AttractionId attractionId = attractionIdRepository.findById(id)
                .orElseThrow(() -> new ErrorException(ErrorCode.ATTRACTION_NOT_FOUND));

        // 좋아요가 있으면 삭제, 없으면 생성
        Optional<AttractionLike> attractionLikeOptional = attractionLikeRepository.findByIdAndUserId(id, user.getId());
        if(attractionLikeOptional.isPresent()) {
            attractionLikeRepository.delete(attractionLikeOptional.get());
        } else {
            attractionLikeRepository.save(
                    AttractionLike.builder()
                            .attractionId(attractionId)
                            .user(user)
                            .build()
            );
        }

        return new ProgressResponse<>(true, id);
    }

    /**
     * 관광지 목록 조회
     */
    public List<AttractionResponse> getLikeAttractions(LanguageCode languageCode) {
        if(!UserInfoUtil.isAuthenticated()) {
            throw new ErrorException(ErrorCode.LOGIN_NOT_ACCESS);
        }

        // 현재 로그인 한 사용자
        User user = userRepository.findById(Objects.requireNonNull(UserInfoUtil.getUserId()))
                .orElseThrow(() -> new ErrorException(ErrorCode.USER_NOT_FOUND));

        // 좋아요 관광지 조회
        List<AttractionResponse> results = new ArrayList<>();

        // id 조회
        List<AttractionId> likeAttractionIds = user.getAttractionLikes().stream().map(AttractionLike::getAttractionId).toList();
        // info 조회
        for(AttractionId id : likeAttractionIds) {

            // info 언어 필터링
            Optional<AttractionInfo> attractionInfoOptional = id.getAttractionInfos().stream()
                    .filter(y -> Objects.equals(y.getLanguageCode(), languageCode))
                    .findAny();

            attractionInfoOptional.ifPresent(
                    info -> results.add(new AttractionResponse(id, info))
            );
        }

        return results;
    }
}
