package com.seoulmate.poppopseoul.domain.challenge.service;

import com.seoulmate.poppopseoul.common.dto.ProgressResponse;
import com.seoulmate.poppopseoul.common.enumeration.LanguageCode;
import com.seoulmate.poppopseoul.domain.attraction.entity.AttractionId;
import com.seoulmate.poppopseoul.domain.attraction.repository.AttractionIdRepository;
import com.seoulmate.poppopseoul.domain.challenge.dto.ChallengeCreateRequest;
import com.seoulmate.poppopseoul.domain.challenge.dto.ChallengeResponse;
import com.seoulmate.poppopseoul.domain.challenge.dto.ChallengeUpdateRequest;
import com.seoulmate.poppopseoul.domain.challenge.entity.ChallengeId;
import com.seoulmate.poppopseoul.domain.challenge.entity.ChallengeInfo;
import com.seoulmate.poppopseoul.domain.challenge.repository.ChallengeIdRepository;
import com.seoulmate.poppopseoul.domain.challenge.repository.ChallengeInfoRepository;
import com.seoulmate.poppopseoul.domain.theme.entity.Theme;
import com.seoulmate.poppopseoul.domain.theme.repository.ThemeRepository;
import com.seoulmate.poppopseoul.exception.ErrorCode;
import com.seoulmate.poppopseoul.exception.ErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChallengeService {

    private final ChallengeIdRepository challengeIdRepository;
    private final ChallengeInfoRepository challengeInfoRepository;
    private final AttractionIdRepository attractionIdRepository;
    private final ThemeRepository themeRepository;
    /**
     * 챌린지 상세 조회
     */
    public ChallengeResponse getDetail(Long id, LanguageCode languageCode) {
        ChallengeId challengeId = challengeIdRepository.findById(id).orElseThrow(() -> new ErrorException(ErrorCode.CHALLENGE_NOT_FOUND));
        ChallengeInfo challengeInfo = challengeInfoRepository.findByChallengeIdAndLanguageCode(challengeId, languageCode)
                .orElseThrow(() -> new ErrorException(ErrorCode.CHALLENGE_NOT_FOUND));

        return new ChallengeResponse(challengeId, challengeInfo, languageCode);
    }

    /**
     * 챌린지 등록
     */
    @Transactional
    public ProgressResponse<ChallengeResponse> create(ChallengeCreateRequest condition) {
        LanguageCode languageCode = condition.getLanguageCode();
        // 테마 id, 관광지 id 세팅
        Theme theme = themeRepository.findById(condition.getThemeId())
                .orElseThrow(() -> new ErrorException(ErrorCode.CHALLENGE_THEME_NOT_FOUND));

        List<AttractionId> attractionIds = attractionIdRepository.findAllById(condition.getAttractionIds());
        if(attractionIds.isEmpty()) {
            throw new ErrorException(ErrorCode.ATTRACTION_NOT_FOUND);
        }
        if(attractionIds.size() > 5) {
            throw new ErrorException(ErrorCode.ATTRACTION_MAX_SIZE);
        }

        // 챌린지 id 등록
        ChallengeId challengeId = challengeIdRepository.save(
                ChallengeId.toEntity(condition, theme, attractionIds));

        // 챌린지 info 등록
        ChallengeInfo infoKor = challengeInfoRepository.save(ChallengeInfo.toEntity(condition, challengeId, LanguageCode.KOR));
        ChallengeInfo infoEng = challengeInfoRepository.save(ChallengeInfo.toEntity(condition, challengeId, LanguageCode.ENG));

        return new ProgressResponse<>(true,
                new ChallengeResponse(challengeId, languageCode.isKorean() ? infoKor : infoEng, languageCode));
    }

    /**
     * 챌린지 수정
     */
    @Transactional
    public ProgressResponse<ChallengeResponse> update(ChallengeUpdateRequest condition) {
        if (challengeIdRepository.findById(condition.getId()).isEmpty()) {
            throw new ErrorException(ErrorCode.CHALLENGE_NOT_FOUND);
        }

        // 테마 id, 관광지 id 세팅
        Theme theme = themeRepository.findById(condition.getCreateRequest().getThemeId())
                .orElseThrow(() -> new ErrorException(ErrorCode.CHALLENGE_THEME_NOT_FOUND));

        List<AttractionId> attractionIds = attractionIdRepository.findAllById(condition.getCreateRequest().getAttractionIds());
        if(attractionIds.isEmpty()) {
            throw new ErrorException(ErrorCode.ATTRACTION_NOT_FOUND);
        }
        if(attractionIds.size() > 5) {
            throw new ErrorException(ErrorCode.ATTRACTION_MAX_SIZE);
        }

        // 챌린지 id 등록
        ChallengeId challengeId = challengeIdRepository.save(
                ChallengeId.toEntity(condition.getCreateRequest(), theme, attractionIds));

        // 챌린지 info 등록
        ChallengeInfo infoKor = challengeInfoRepository.save(ChallengeInfo.toEntity(condition.getCreateRequest(), challengeId, LanguageCode.KOR));
        ChallengeInfo infoEng = challengeInfoRepository.save(ChallengeInfo.toEntity(condition.getCreateRequest(), challengeId, LanguageCode.ENG));

        return new ProgressResponse<>(true,
                new ChallengeResponse(challengeId, condition.getCreateRequest().getLanguageCode().isKorean()
                        ? infoKor : infoEng,
                        condition.getCreateRequest().getLanguageCode()));
    }

    /**
     * 챌린지 삭제
     */
    @Transactional
    public ProgressResponse<Long> delete(Long id) {
        ChallengeId challengeId = challengeIdRepository.findById(id).orElseThrow(() -> new ErrorException(ErrorCode.CHALLENGE_NOT_FOUND));
        challengeIdRepository.delete(challengeId);
        return new ProgressResponse<>(true, id);
    }

}
