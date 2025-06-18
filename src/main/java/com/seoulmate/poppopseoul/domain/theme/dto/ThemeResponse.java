package com.seoulmate.poppopseoul.domain.theme.dto;

import com.seoulmate.poppopseoul.common.enumeration.LanguageCode;
import com.seoulmate.poppopseoul.domain.theme.entity.Theme;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThemeResponse {
    @Schema(description = "테마 id")
    private Long id;
    @Schema(description = "테마명")
    private String name;
    @Schema(description = "테마 설명")
    private String description;

    public ThemeResponse(Theme theme, LanguageCode languageCode) {
        this.id = theme.getId();
        this.name = languageCode.isKorean() ? theme.getNameKor() : theme.getNameEng();
        this.description = languageCode.isKorean() ? theme.getDescriptionKor() : theme.getDescriptionEng();
    }
}
