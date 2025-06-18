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
    @Schema(description = "관광지명")
    private String name;
    @Schema(description = "관광지 설명")
    private String description;
    @Schema(description = "관광지 분류")
    private List<AttractionDetailCode> detailCodes;
    @Schema(description = "관광지 주소")
    private String address;
    @Schema(description = "홈페이지 url")
    private String homepageUrl;
    @Schema(description = "X 좌표")
    private Double locationX;
    @Schema(description = "Y 좌표")
    private Double locationY;
    @Schema(description = "전화번호")
    private String tel;
    @Schema(description = "교통정보")
    private String subway;
    @Schema(description = "이미지 url")
    private String imageUrl;

    public AttractionId toEntityId() {
        AttractionId attractionId = new AttractionId();
        attractionId.setAttractionDetailCodes(detailCodes);
        attractionId.setOriginKey("3333");
        return attractionId;
    }

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
