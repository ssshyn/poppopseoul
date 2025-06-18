package com.seoulmate.poppopseoul.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LanguageCode {
    KOR, ENG;

    public boolean isKorean() {
        return this.equals(LanguageCode.KOR);
    }
}
