package com.seoulmate.poppopseoul.domain.challenge.controller;

import com.seoulmate.poppopseoul.common.dto.ProgressResponse;
import com.seoulmate.poppopseoul.common.enumeration.LanguageCode;
import com.seoulmate.poppopseoul.domain.challenge.dto.ChallengeCreateRequest;
import com.seoulmate.poppopseoul.domain.challenge.dto.ChallengeResponse;
import com.seoulmate.poppopseoul.domain.challenge.dto.ChallengeUpdateRequest;
import com.seoulmate.poppopseoul.domain.challenge.service.ChallengeService;
import com.seoulmate.poppopseoul.exception.ErrorCode;
import com.seoulmate.poppopseoul.exception.ErrorResponse;
import com.seoulmate.poppopseoul.exception.annotation.ApiErrorCodeExample;
import com.seoulmate.poppopseoul.exception.annotation.ApiErrorCodeExamples;
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

@Tag(name = "챌린지 controller", description = "챌린지 관리 API 목록")
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
@RequestMapping("challenge")
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @Operation(summary = "챌린지 상세조회", description = "챌린지 상세조회")
    @ApiErrorCodeExamples({ErrorCode.CHALLENGE_NOT_FOUND, ErrorCode.USER_NOT_FOUND})
    @GetMapping
    public ResponseEntity<List<ChallengeResponse>> getList() {
        return ResponseEntity.ok(new ArrayList<>());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChallengeResponse> getDetail(@PathVariable(value = "id") Long id,
                                                       @RequestParam(value = "languageCode")LanguageCode languageCode) {
        return ResponseEntity.ok(challengeService.getDetail(id, languageCode));
    }

    @Operation(summary = "챌린지 등록", description = "챌린지 등록")
    @ApiErrorCodeExamples({ErrorCode.ATTRACTION_NOT_FOUND, ErrorCode.CHALLENGE_THEME_NOT_FOUND,
            ErrorCode.REQUIRED_PARAMETER, ErrorCode.MAX_SIZE})
    @PostMapping
    public ResponseEntity<ProgressResponse<ChallengeResponse>> create(@RequestBody ChallengeCreateRequest condition) {
        return ResponseEntity.ok(challengeService.create(condition));
    }

    @Operation(summary = "챌린지 수정", description = "챌린지 수정")
    @ApiErrorCodeExamples({ErrorCode.ATTRACTION_NOT_FOUND, ErrorCode.CHALLENGE_THEME_NOT_FOUND,
            ErrorCode.REQUIRED_PARAMETER, ErrorCode.MAX_SIZE})
    @PutMapping
    public ResponseEntity<ProgressResponse<ChallengeResponse>> update(@RequestBody ChallengeUpdateRequest condition) {
        return ResponseEntity.ok(challengeService.update(condition));
    }

    @Operation(summary = "챌린지 삭제", description = "챌린지 삭제")
    @ApiErrorCodeExample(ErrorCode.CHALLENGE_NOT_FOUND)
    @DeleteMapping("/{id}")
    public ResponseEntity<ProgressResponse<Long>> delete(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(challengeService.delete(id));
    }
}
