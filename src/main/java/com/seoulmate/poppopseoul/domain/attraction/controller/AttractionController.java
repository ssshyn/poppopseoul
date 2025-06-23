package com.seoulmate.poppopseoul.domain.attraction.controller;

import com.seoulmate.poppopseoul.common.dto.ProgressResponse;
import com.seoulmate.poppopseoul.common.enumeration.LanguageCode;
import com.seoulmate.poppopseoul.domain.attraction.dto.AttractionCreateRequest;
import com.seoulmate.poppopseoul.domain.attraction.dto.AttractionResponse;
import com.seoulmate.poppopseoul.domain.attraction.dto.AttractionUpdateRequest;
import com.seoulmate.poppopseoul.domain.attraction.service.AttractionService;
import com.seoulmate.poppopseoul.exception.ErrorCode;
import com.seoulmate.poppopseoul.exception.ErrorResponse;
import com.seoulmate.poppopseoul.exception.annotation.ApiErrorCodeExample;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "관광지 controller", description = "관광지 관리 API 목록")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "403", description = "BAD REQUEST", content = @Content(
                mediaType = "application/json",
                examples = {
                        @ExampleObject(name = "A0010", description = "만료된 엑세스 토큰입니다.",
                                value = """
                                        {"code": "A0010", "message": "만료된 엑세스 토큰입니다."}
                                        """)
                }, schema = @Schema(implementation = ErrorResponse.class)
        )),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content(
                mediaType = "application/json",
                examples = {
                        @ExampleObject(name = "500", description = "INTERNAL SERVER ERROR",
                                value = """
                                        {"status": 500, "message": "INTERNAL SERVER ERROR"}
                                        """)
                }, schema = @Schema(implementation = ErrorResponse.class)
        ))
})
@RestController
@RequestMapping("attraction")
@RequiredArgsConstructor
public class AttractionController {

    private final AttractionService attractionService;

    @Operation(summary = "목록 조회", description = "목록 조회")
    @GetMapping
    public ResponseEntity<List<AttractionResponse>> getList() {
        return ResponseEntity.ok(new ArrayList<>());
    }

    @Operation(summary = "관광지 상세 조회", description = "관광지 상세 조회")
    @ApiErrorCodeExample(ErrorCode.ATTRACTION_NOT_FOUND)
    @GetMapping("/{id}")
    public ResponseEntity<AttractionResponse> getDetail(@PathVariable(value = "id") Long id,
                                                        @RequestParam(value = "languageCode") LanguageCode languageCode) {
        return ResponseEntity.ok(attractionService.getDetail(id, languageCode));
    }

    @Operation(summary = "관광지 등록", description = "관광지 등록")
    @PostMapping
    public ResponseEntity<ProgressResponse<AttractionResponse>> saveAttraction(@RequestBody AttractionCreateRequest condition) {
        return ResponseEntity.ok(attractionService.createAttraction(condition));
    }

    @Operation(summary = "관광지 수정", description = "관광지 수정")
    @ApiErrorCodeExample(ErrorCode.ATTRACTION_NOT_FOUND)
    @PutMapping
    public ResponseEntity<ProgressResponse<AttractionResponse>> updateAttraction(@RequestBody AttractionUpdateRequest condition) {
        return ResponseEntity.ok(attractionService.updateAttraction(condition));
    }

    @Operation(summary = "관광지 삭제", description = "관광지 삭제")
    @ApiErrorCodeExample(ErrorCode.ATTRACTION_NOT_FOUND)
    @DeleteMapping("/{id}")
    public ResponseEntity<ProgressResponse<Long>> deleteAttraction(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(attractionService.deleteAttraction(id));
    }


    // -- 부가 --
    // 좋아요한 목록 조회
    // 좋아요 등록/취소
    // 관광지 스탬프 등록

}
