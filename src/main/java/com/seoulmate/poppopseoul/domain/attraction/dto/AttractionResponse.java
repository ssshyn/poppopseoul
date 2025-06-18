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
public class AttractionResponse {
    @Schema(description = "관광지 id")
    private Long id;
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

    public AttractionResponse(AttractionId attractionId, AttractionInfo attractionInfo) {
        this.id = attractionId.getId();
        this.languageCode = attractionInfo.getLanguageCode();
        this.name = attractionInfo.getName();
        this.description = attractionInfo.getDescription();
        this.detailCodes = attractionId.getAttractionDetailCodes();
        this.address = attractionInfo.getAddress();
        this.homepageUrl = attractionInfo.getHomepageUrl();
        this.locationX = attractionInfo.getLocationX();
        this.locationY = attractionInfo.getLocationY();
        this.tel = attractionInfo.getTel();
        this.subway = attractionInfo.getSubway();
        this.imageUrl = attractionInfo.getImageUrl();
    }
//    @Schema(description = "좋아요 수")
//    private Integer likes;
//    @Schema(description = "좋아요 여부")
//    private Boolean isLiked;
//    @Schema(description = "스탬프 찍은 사람 수")
//    private Integer stampCount;
}
