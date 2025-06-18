package com.seoulmate.poppopseoul.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProgressResponse<T> {
    private boolean isCompleted;
    private T entity;
}
