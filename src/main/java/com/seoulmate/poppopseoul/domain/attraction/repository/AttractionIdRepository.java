package com.seoulmate.poppopseoul.domain.attraction.repository;

import com.seoulmate.poppopseoul.domain.attraction.entity.AttractionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttractionIdRepository extends JpaRepository<AttractionId, Long> {
}
