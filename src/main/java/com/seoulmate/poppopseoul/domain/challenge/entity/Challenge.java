package com.seoulmate.poppopseoul.domain.challenge.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.seoulmate.poppopseoul.domain.challenge.enumeration.DisplayRank;
import com.seoulmate.poppopseoul.domain.challenge.enumeration.Level;
import com.seoulmate.poppopseoul.domain.theme.entity.Theme;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@DynamicUpdate
@Table(name = "challenge")
public class Challenge {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String nameEng;

    @Column(nullable = false)
    private String titleEng;

    @Column
    private String description;

    @Column
    private String descriptionEng;

    @Column
    private String mainLocation;

    @Column
    private String mainLocationEng;

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
}
