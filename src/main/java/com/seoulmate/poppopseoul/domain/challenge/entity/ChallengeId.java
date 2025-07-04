package com.seoulmate.poppopseoul.domain.challenge.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.seoulmate.poppopseoul.domain.attraction.entity.AttractionId;
import com.seoulmate.poppopseoul.domain.challenge.dto.ChallengeCreateRequest;
import com.seoulmate.poppopseoul.domain.challenge.enumeration.DisplayRank;
import com.seoulmate.poppopseoul.domain.challenge.enumeration.Level;
import com.seoulmate.poppopseoul.domain.theme.entity.Theme;
import jakarta.persistence.*;
import lombok.*;
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
@Table(name = "challenge_id")
public class ChallengeId {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private DisplayRank displayRank;

    @Enumerated(EnumType.STRING)
    private Level level;

    @ManyToOne
    @JoinColumn(name = "theme_id")
    @JsonBackReference
    private Theme theme;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime modifiedAt;

    @ManyToMany
    @JoinTable(
            name = "challenge_attraction",
            joinColumns = @JoinColumn(name = "challenge_id"),
            inverseJoinColumns = @JoinColumn(name = "attraction_id")
    )
    private List<AttractionId> attractionIds = new ArrayList<>();

    @OneToMany(mappedBy = "challengeId", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ChallengeInfo> challengeInfos = new ArrayList<>();

    public ChallengeId(Long id) {
        this.id = id;
    }

    public static ChallengeId toEntity(ChallengeCreateRequest condition, Theme theme, List<AttractionId> attractionIds) {
        ChallengeId challengeId = new ChallengeId();
        challengeId.setImageUrl(condition.getImageUrl());
        challengeId.setDisplayRank(condition.getDisplayRank());
        challengeId.setLevel(condition.getLevel());
        challengeId.setTheme(theme);
        challengeId.setAttractionIds(attractionIds);
        return challengeId;
    }
}
