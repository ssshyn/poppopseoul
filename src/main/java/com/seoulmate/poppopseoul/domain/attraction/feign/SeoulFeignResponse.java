package com.seoulmate.poppopseoul.domain.attraction.feign;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seoulmate.poppopseoul.domain.attraction.feign.dto.MountainParkResponse;
import com.seoulmate.poppopseoul.domain.attraction.feign.dto.TourSpotResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeoulFeignResponse<T> {
    @Schema(description = "산과공원 response")
    @JsonProperty("SebcParkTourKor")
    private SeoulApiResponse<MountainParkResponse> mountainParkResponse;

    @Schema(description = "산과공원 response")
    @JsonProperty("TbVwAttractions")
    private SeoulApiResponse<TourSpotResponse> tourSpotResponse;
}
