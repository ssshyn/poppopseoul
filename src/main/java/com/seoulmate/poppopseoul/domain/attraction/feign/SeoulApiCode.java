package com.seoulmate.poppopseoul.domain.attraction.feign;

import com.seoulmate.poppopseoul.domain.attraction.feign.dto.MountainParkResponse;
import com.seoulmate.poppopseoul.domain.attraction.feign.dto.TourSpotResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SeoulApiCode {

//    NIGHT_VIEW("viewNightSpot", "야경명소 api", NightViewFeignResponse.class),
//    HANOK("ViewBcgImpFacil", "공공한옥 api", HanokFeignResponse .class),
//    TOUR_ROAD("SebcTourStreetKor", "관광거리 api", TourRoadFeignResponse.class),
//    CULTURAL("culturalEventInfo", "문화행사 api", CulturalFeignResponse.class);
    TOUR_SPOT("TbVwAttractions", "관광명소 api", TourSpotResponse.class, 1, 1000),
    MOUNTAIN_PARK("SebcParkTourKor", "산과공원 api", MountainParkResponse.class, 1, 1000);

    final String apiCode;
    final String description;
    final Class<?> classType;
    final Integer startIndex;
    final Integer endIndex;
}
