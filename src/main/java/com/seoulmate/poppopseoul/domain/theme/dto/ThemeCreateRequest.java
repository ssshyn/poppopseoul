package com.seoulmate.poppopseoul.domain.theme.dto;

import com.seoulmate.poppopseoul.common.enumeration.LanguageCode;
import com.seoulmate.poppopseoul.domain.challenge.entity.ChallengeId;
import com.seoulmate.poppopseoul.domain.theme.entity.Theme;
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
public class ThemeCreateRequest {
    @Schema(description = "테마 id")
    private Long id;
    @Schema(description = "언어 코드")
    private LanguageCode languageCode;
    @Schema(description = "테마명_국문")
    private String name;
    @Schema(description = "테마명_영문")
    private String nameEng;
    @Schema(description = "테마 설명_국문")
    private String description;
    @Schema(description = "테마 설명_영문")
    private String descriptionEng;
    @Schema(description = "챌린지 id 목록")
    private List<Long> challengeIds;

    public Theme toEntity() {
        Theme theme = new Theme();
        theme.setNameKor(name);
        theme.setNameEng(nameEng);
        theme.setDescriptionKor(description);
        theme.setDescriptionEng(descriptionEng);
        theme.setChallenges(challengeIds.stream().map(ChallengeId::new).toList());
        return theme;
    }
}
