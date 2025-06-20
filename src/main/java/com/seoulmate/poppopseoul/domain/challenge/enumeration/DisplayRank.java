package com.seoulmate.poppopseoul.domain.challenge.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum DisplayRank {
    LOW(1),
    MEDIUM(2),
    HIGH(3),
    CULTURE(9);

    private Integer rankNum;

    public Integer getDisplayNum() {
        if(Objects.equals(this, CULTURE)) {
            return 0;
        } else {
            return this.rankNum;
        }
    }
}
