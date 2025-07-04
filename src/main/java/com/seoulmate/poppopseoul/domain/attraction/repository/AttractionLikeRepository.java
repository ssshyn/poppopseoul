package com.seoulmate.poppopseoul.domain.attraction.repository;

import com.seoulmate.poppopseoul.domain.attraction.entity.AttractionLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttractionLikeRepository extends JpaRepository<AttractionLike, Long> {
    Optional<AttractionLike> findByIdAndUserId(Long id, Long userId);
}
