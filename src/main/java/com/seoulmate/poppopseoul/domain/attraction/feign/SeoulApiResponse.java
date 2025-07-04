package com.seoulmate.poppopseoul.domain.attraction.feign;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeoulApiResponse<T> {
    @JsonProperty("list_total_count")
    private Integer listTotalCount;
    @JsonProperty("RESULT")
    private Result result;
    @JsonProperty("row")
    private List<T> row;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {
        @JsonProperty("CODE")
        private String code;
        @JsonProperty("MESSAGE")
        private String message;
    }
}
