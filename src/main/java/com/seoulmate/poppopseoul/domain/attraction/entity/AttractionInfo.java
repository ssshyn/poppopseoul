package com.seoulmate.poppopseoul.domain.attraction.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.seoulmate.poppopseoul.common.enumeration.LanguageCode;
import com.seoulmate.poppopseoul.domain.attraction.dto.AttractionCreateRequest;
import com.seoulmate.poppopseoul.domain.attraction.feign.dto.MountainParkResponse;
import com.seoulmate.poppopseoul.domain.attraction.feign.dto.TourSpotResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@DynamicUpdate
@Table(name = "attraction_info")
public class AttractionInfo {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LanguageCode languageCode;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String address;

    @Column
    private Double locationX;

    @Column
    private Double locationY;

    @Column
    private String homepageUrl;

    @Column
    private String tel;

    @Column
    private String subway;

    @Column
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "attraction_id")
    @JsonBackReference
    private AttractionId attractionId;

    public static AttractionInfo toEntity(AttractionCreateRequest condition, AttractionId attractionId, LanguageCode languageCode) {
        return AttractionInfo.builder()
                .languageCode(languageCode)
                .name(languageCode.isKorean() ? condition.getName() : condition.getNameEng())
                .description(languageCode.isKorean() ? condition.getDescription() : condition.getDescriptionEng())
                .address(languageCode.isKorean() ? condition.getAddress() : condition.getAddressEng())
                .locationX(condition.getLocationX())
                .locationY(condition.getLocationY())
                .homepageUrl(condition.getHomepageUrl())
                .tel(condition.getTel())
                .subway(languageCode.isKorean() ? condition.getSubway() : condition.getSubwayEng())
                .imageUrl(condition.getImageUrl())
                .attractionId(attractionId)
                .build();
    }

    public static <T> AttractionInfo saveApiInfo(T item, AttractionId attractionId) {
        Class<?> itemClass = item.getClass();
        AttractionInfo attractionInfo = new AttractionInfo();

        if (Objects.equals(itemClass, MountainParkResponse.class)) {
            attractionInfo = new AttractionInfo((MountainParkResponse) item);
        }
        if (Objects.equals(itemClass, TourSpotResponse.class)) {
            attractionInfo = new AttractionInfo((TourSpotResponse) item);
        }

        attractionInfo.setAttractionId(attractionId);
        return attractionInfo;
    }

    public AttractionInfo(MountainParkResponse response) {
        this.languageCode = LanguageCode.KOR;
        this.name = response.getName();
        this.address = response.getAddress();
        this.tel = response.getTel();
        this.subway = response.getSubway();
    }

    public AttractionInfo(TourSpotResponse response) {
        this.languageCode = LanguageCode.KOR;
        this.name = response.getName();
        this.address = response.getAddress();
        this.tel = response.getTel();
        this.homepageUrl = response.getHompage();
        this.subway = response.getSubway();
        this.description = response.getDescription();
    }
}
