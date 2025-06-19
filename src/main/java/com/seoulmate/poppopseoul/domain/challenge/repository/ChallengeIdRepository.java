package com.seoulmate.poppopseoul.domain.challenge.repository;

import com.seoulmate.poppopseoul.domain.challenge.entity.ChallengeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeIdRepository extends JpaRepository<ChallengeId, Long> {
}
