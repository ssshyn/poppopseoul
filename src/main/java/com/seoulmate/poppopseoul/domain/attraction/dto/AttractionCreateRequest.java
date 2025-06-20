package com.seoulmate.poppopseoul.domain.attraction.dto;

import com.seoulmate.poppopseoul.common.enumeration.LanguageCode;
import com.seoulmate.poppopseoul.domain.attraction.entity.AttractionId;
import com.seoulmate.poppopseoul.domain.attraction.entity.AttractionInfo;
import com.seoulmate.poppopseoul.domain.attraction.enumeration.AttractionDetailCode;
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
public class AttractionCreateRequest {
    @Schema(description = "언어 코드")
    private LanguageCode languageCode;

    // 관광지 info
    @Schema(description = "관광지명_국문")
    private String name;
    @Schema(description = "관광지명_영문")
    private String nameEng;
    @Schema(description = "관광지설명_국문")
    private String description;
    @Schema(description = "관광지설명_영문")
    private String descriptionEng;
    @Schema(description = "교통정보_국문")
    private String subway;
    @Schema(description = "교통정보_영문")
    private String subwayEng;
    @Schema(description = "관광지주소_국문")
    private String address;
    @Schema(description = "관광지주소_영문")
    private String addressEng;
    @Schema(description = "홈페이지 url")
    private String homepageUrl;
    @Schema(description = "X 좌표")
    private Double locationX;
    @Schema(description = "Y 좌표")
    private Double locationY;
    @Schema(description = "전화번호")
    private String tel;
    @Schema(description = "이미지 url")
    private String imageUrl;

    // 관광지 id
    @Schema(description = "관광지 분류")
    private List<AttractionDetailCode> detailCodes;

    public AttractionInfo toEntityInfo(AttractionId attractionId) {
        return AttractionInfo.builder()
                .attractionId(attractionId)
                .languageCode(languageCode)
                .name(name)
                .description(description)
                .address(address)
                .locationX(locationX)
                .locationY(locationY)
                .homepageUrl(homepageUrl)
                .tel(tel)
                .subway(subway)
                .imageUrl(imageUrl)
                .build();
    }
}
