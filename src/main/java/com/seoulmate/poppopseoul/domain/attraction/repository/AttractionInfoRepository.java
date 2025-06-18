package com.seoulmate.poppopseoul.domain.attraction.repository;

import com.seoulmate.poppopseoul.common.enumeration.LanguageCode;
import com.seoulmate.poppopseoul.domain.attraction.entity.AttractionId;
import com.seoulmate.poppopseoul.domain.attraction.entity.AttractionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttractionInfoRepository extends JpaRepository<AttractionInfo, Long> {
    Optional<AttractionInfo> findByAttractionIdAndLanguageCode(AttractionId attractionId, LanguageCode languageCode);
}
