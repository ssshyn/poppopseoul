package com.seoulmate.poppopseoul.domain.challenge.dto;

import com.seoulmate.poppopseoul.common.enumeration.LanguageCode;
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
public class ChallengeCreateRequest {
    @Schema(description = "언어 코드")
    private LanguageCode languageCode;

    // 챌린지 info
    @Schema(description = "챌린지명_국문")
    private String name;
    @Schema(description = "챌린지명_영문")
    private String nameEng;
    @Schema(description = "챌린지소제목_국문")
    private String title;
    @Schema(description = "챌린지소제목_영문")
    private String titleEng;
    @Schema(description = "챌린지설명_국문")
    private String description;
    @Schema(description = "챌린지설명_영문")
    private String descriptionEng;
    @Schema(description = "주요지역구_국문")
    private String mainLocation;
    @Schema(description = "주요지역구_영문")
    private String mainLocationEng;

    // 챌린지 id
    @Schema(description = "이미지 url")
    private String imageUrl;
    @Schema(description = "노출 순위")
    private DisplayRank displayRank;
    @Schema(description = "난이도")
    private Level level;
    @Schema(description = "챌린지 테마 ID")
    private Long themeId;
    @Schema(description = "관광지 ID 목록")
    private List<Long> attractionIds;

}
