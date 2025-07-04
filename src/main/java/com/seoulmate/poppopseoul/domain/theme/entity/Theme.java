package com.seoulmate.poppopseoul.domain.theme.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.seoulmate.poppopseoul.domain.challenge.entity.ChallengeId;
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
@Table(name = "theme")
public class Theme {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String nameKor;

    @Column
    private String nameEng;

    @Column(nullable = false)
    private String descriptionKor;

    @Column
    private String descriptionEng;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ChallengeId> challenges = new ArrayList<>();
}
