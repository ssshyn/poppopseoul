package com.seoulmate.poppopseoul.domain.challenge.dto;

import com.seoulmate.poppopseoul.common.enumeration.LanguageCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeUpdateRequest{
    @Schema(description = "챌린지 id")
    private Long id;
    @Schema(description = "수정 내용")
    private ChallengeCreateRequest createRequest;
}
