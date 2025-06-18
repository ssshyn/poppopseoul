package com.seoulmate.poppopseoul.domain.user.entity;

import com.seoulmate.poppopseoul.common.enumeration.LoginType;
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
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Column(nullable = false)
    private String nickname;

//    // 댓글 목록
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Comment> comments = new ArrayList<>();
//
//    // 장소 스탬프
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<VisitStamp> visitStamps = new ArrayList<>();
//
//    // 관광지 찜 여부
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<AttractionLikes> attractionLikes = new ArrayList<>();
//
//    // 챌린지 상태
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<ChallengeStatus> challengeStatuses = new ArrayList<>();
//
//    // 챌린지 찜 여부
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<ChallengeLikes> challengeLikes = new ArrayList<>();

    public static User of(String userId, LoginType loginType, String nickname) {
        return User.builder()
                .email(userId)
                .loginType(loginType)
                .nickname(nickname)
                .build();
    }
}
