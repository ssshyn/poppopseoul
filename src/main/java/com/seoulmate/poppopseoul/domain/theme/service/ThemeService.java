package com.seoulmate.poppopseoul.domain.theme.service;

import com.seoulmate.poppopseoul.common.dto.ProgressResponse;
import com.seoulmate.poppopseoul.common.enumeration.LanguageCode;
import com.seoulmate.poppopseoul.domain.theme.dto.ThemeCreateRequest;
import com.seoulmate.poppopseoul.domain.theme.dto.ThemeResponse;
import com.seoulmate.poppopseoul.domain.theme.entity.Theme;
import com.seoulmate.poppopseoul.domain.theme.repository.ThemeRepository;
import com.seoulmate.poppopseoul.exception.ErrorCode;
import com.seoulmate.poppopseoul.exception.ErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ThemeService {

    private final ThemeRepository themeRepository;

    /**
     * 목록 조회
     */
    public List<ThemeResponse> getList(LanguageCode languageCode) {
        List<Theme> themes = themeRepository.findAll();
        return themes.stream().map(entity -> new ThemeResponse(entity, languageCode)).toList();
    }

    /**
     * 등록
     */
    @Transactional
    public ProgressResponse<ThemeResponse> create(ThemeCreateRequest themeCreateRequest) {
        Theme theme = themeRepository.save(themeCreateRequest.toEntity());
        return new ProgressResponse<>(true, new ThemeResponse(theme, themeCreateRequest.getLanguageCode()));
    }

    /**
     * 삭제
     */
    @Transactional
    public ProgressResponse<Long> delete(Long id) {
        Theme theme = themeRepository.findById(id).orElseThrow(() -> new ErrorException(ErrorCode.CHALLENGE_THEME_NOT_FOUND));
        themeRepository.delete(theme);
        return new ProgressResponse<>(true, id);
    }
}
