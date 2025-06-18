package com.seoulmate.poppopseoul.domain.theme.repository;

import com.seoulmate.poppopseoul.domain.theme.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {
}
