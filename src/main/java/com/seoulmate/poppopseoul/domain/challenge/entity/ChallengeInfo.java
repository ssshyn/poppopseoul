package com.seoulmate.poppopseoul.domain.challenge.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.seoulmate.poppopseoul.common.enumeration.LanguageCode;
import com.seoulmate.poppopseoul.domain.challenge.dto.ChallengeCreateRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@DynamicUpdate
@Table(name = "challenge_info")
public class ChallengeInfo {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LanguageCode languageCode;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column
    private String mainLocation;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    @JsonBackReference
    private ChallengeId challengeId;

    public static ChallengeInfo toEntity(ChallengeCreateRequest condition, ChallengeId challengeId, LanguageCode languageCode) {
        return ChallengeInfo.builder()
                .languageCode(languageCode)
                .name(languageCode.isKorean() ? condition.getName() : condition.getNameEng())
                .title(languageCode.isKorean() ? condition.getTitle() : condition.getTitleEng())
                .description(languageCode.isKorean() ? condition.getDescription() : condition.getDescriptionEng())
                .mainLocation(languageCode.isKorean() ? condition.getMainLocation() : condition.getMainLocationEng())
                .challengeId(challengeId)
                .build();
    }
}
