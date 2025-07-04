package com.seoulmate.poppopseoul.domain.attraction.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.seoulmate.poppopseoul.domain.attraction.dto.AttractionCreateRequest;
import com.seoulmate.poppopseoul.domain.attraction.enumeration.AttractionDetailCode;
import com.seoulmate.poppopseoul.domain.challenge.entity.ChallengeId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@DynamicUpdate
@Table(name = "attraction_id")
public class AttractionId {
    @Id
    @GeneratedValue
    private Long id;

//    @Column(nullable = false)
//    private String originKey;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<AttractionDetailCode> attractionDetailCodes;

    @OneToMany(mappedBy = "attractionId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<AttractionInfo> attractionInfos = new ArrayList<>();

    @ManyToMany(mappedBy = "attractionIds")
    private List<ChallengeId> challenges = new ArrayList<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime modifiedAt;

    public AttractionId(List<AttractionDetailCode> detailCodes) {
        this.attractionDetailCodes = detailCodes;
    }

    public static AttractionId toEntity(AttractionCreateRequest condition) {
        AttractionId attractionId = new AttractionId();
        attractionId.attractionDetailCodes = condition.getDetailCodes();
        return attractionId;
    }
}
