package com.seoulmate.poppopseoul.domain.challenge.repository;

import com.seoulmate.poppopseoul.common.enumeration.LanguageCode;
import com.seoulmate.poppopseoul.domain.challenge.entity.ChallengeId;
import com.seoulmate.poppopseoul.domain.challenge.entity.ChallengeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChallengeInfoRepository extends JpaRepository<ChallengeInfo, Long> {
    List<ChallengeInfo> findByChallengeId(ChallengeId challengeId);
    Optional<ChallengeInfo> findByChallengeIdAndLanguageCode(ChallengeId challengeId, LanguageCode languageCode);
}
