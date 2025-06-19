package com.seoulmate.poppopseoul.domain.challenge.dto;

import com.seoulmate.poppopseoul.common.enumeration.LanguageCode;
import com.seoulmate.poppopseoul.domain.attraction.dto.AttractionResponse;
import com.seoulmate.poppopseoul.domain.challenge.entity.ChallengeId;
import com.seoulmate.poppopseoul.domain.challenge.entity.ChallengeInfo;
import com.seoulmate.poppopseoul.domain.challenge.enumeration.DisplayRank;
import com.seoulmate.poppopseoul.domain.challenge.enumeration.Level;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeResponse {
    @Schema(description = "챌린지 id", example = "3")
    private Long id;
    @Schema(description = "챌린지 이름", example = "서촌 골목의 하루")
    private String name;
    @Schema(description = "챌린지 타이틀", example = "통인시장부터 청와대 앞길까지")
    private String title;
    @Schema(description = "챌린지 설명", example = "전통시장과 공공 문화시설이 어우러진 서촌 일대의 지역적 매력을 모두 담아보세요.")
    private String description;
    @Schema(description = "챌린지 이미지 url")
    private String imageUrl;
    @Schema(description = "주요 동네", example = "서촌")
    private String mainLocation;
    @Schema(description = "챌린지 테마 id", example = "1")
    private Long challengeThemeId;
    @Schema(description = "챌린지 테마명", example = "지역 탐방")
    private String challengeThemeName;
    @Schema(description = "노출 우선 순위", example = "MEDIUM")
    private DisplayRank displayRank;
    @Schema(description = "난이도", example = "EASY")
    private Level level;
    @Schema(description = "관광지 목록")
    private List<AttractionResponse> attractions;

    public ChallengeResponse(ChallengeId challengeId, ChallengeInfo challengeInfo, LanguageCode languageCode) {
        this.id = challengeId.getId();
        this.name = challengeInfo.getName();
        this.title = challengeInfo.getTitle();
        this.description = challengeInfo.getDescription();
        this.imageUrl = challengeId.getImageUrl();
        this.mainLocation = challengeInfo.getMainLocation();
        this.challengeThemeId = challengeId.getTheme().getId();
        this.challengeThemeName = languageCode.isKorean() ? challengeId.getTheme().getNameKor() : challengeId.getTheme().getNameEng();
        this.displayRank = challengeId.getDisplayRank();
        this.level = challengeId.getLevel();
    }
}
