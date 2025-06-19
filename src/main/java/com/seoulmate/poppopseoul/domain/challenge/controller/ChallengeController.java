package com.seoulmate.poppopseoul.domain.challenge.controller;

import com.seoulmate.poppopseoul.common.dto.ProgressResponse;
import com.seoulmate.poppopseoul.common.enumeration.LanguageCode;
import com.seoulmate.poppopseoul.domain.challenge.dto.ChallengeCreateRequest;
import com.seoulmate.poppopseoul.domain.challenge.dto.ChallengeResponse;
import com.seoulmate.poppopseoul.domain.challenge.dto.ChallengeUpdateRequest;
import com.seoulmate.poppopseoul.domain.challenge.service.ChallengeService;
import com.seoulmate.poppopseoul.exception.ErrorResponse;
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
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "R0002", description = "챌린지 정보를 조회할 수 없습니다. 다시 확인해 주세요.",
                                    value = """
                                            {"code": "R0002", "message": "챌린지 정보를 조회할 수 없습니다. 다시 확인해 주세요."}
                                            """)
                    }, schema = @Schema(implementation = ErrorResponse.class)
            )),
            @ApiResponse(responseCode = "401", description = "USER_NOT_FOUND", content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "U0002", description = "존재하지 않는 유저입니다.",
                                    value = """
                                            {"code": "U0002", "message": "존재하지 않는 유저입니다."}
                                            """)
                    }, schema = @Schema(implementation = ErrorResponse.class)
            ))
    })
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
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "R0001", description = "관광지 정보를 조회할 수 없습니다. 다시 확인해 주세요.",
                                    value = """
                                            {"code": "R0001", "message": "관광지 정보를 조회할 수 없습니다. 다시 확인해 주세요."}
                                            """),
                            @ExampleObject(name = "R0003", description = "챌린지 테마 정보를 조회할 수 없습니다. 다시 확인해 주세요.",
                                    value = """
                                            {"code": "R0003", "message": "테마 정보를 조회할 수 없습니다. 다시 확인해 주세요."}
                                            """),
                            @ExampleObject(name = "R0004", description = "필수값이 입력되지 않았습니다. 다시 확인해 주세요.",
                                    value = """
                                            {"code": "R0004", "message": "필수값이 입력되지 않았습니다. 다시 확인해 주세요."}
                                            """),
                            @ExampleObject(name = "R0006", description = "파라미터 최대 값을 초과하였습니다.",
                                    value = """
                                            {"code": "R0006", "message": "파라미터 최대 값을 초과하였습니다."}
                                            """)
                    }, schema = @Schema(implementation = ErrorResponse.class)
            ))
    })
    @PostMapping
    public ResponseEntity<ProgressResponse<ChallengeResponse>> create(@RequestBody ChallengeCreateRequest condition) {
        return ResponseEntity.ok(challengeService.create(condition));
    }

    @Operation(summary = "챌린지 수정", description = "챌린지 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "R0001", description = "관광지 정보를 조회할 수 없습니다. 다시 확인해 주세요.",
                                    value = """
                                            {"code": "R0001", "message": "관광지 정보를 조회할 수 없습니다. 다시 확인해 주세요."}
                                            """),
                            @ExampleObject(name = "R0003", description = "챌린지 테마 정보를 조회할 수 없습니다. 다시 확인해 주세요.",
                                    value = """
                                            {"code": "R0003", "message": "테마 정보를 조회할 수 없습니다. 다시 확인해 주세요."}
                                            """),
                            @ExampleObject(name = "R0004", description = "필수값이 입력되지 않았습니다. 다시 확인해 주세요.",
                                    value = """
                                            {"code": "R0004", "message": "필수값이 입력되지 않았습니다. 다시 확인해 주세요."}
                                            """),
                            @ExampleObject(name = "R0006", description = "파라미터 최대 값을 초과하였습니다.",
                                    value = """
                                            {"code": "R0006", "message": "파라미터 최대 값을 초과하였습니다."}
                                            """)
                    }, schema = @Schema(implementation = ErrorResponse.class)
            ))
    })
    @PutMapping
    public ResponseEntity<ProgressResponse<ChallengeResponse>> update(@RequestBody ChallengeUpdateRequest condition) {
        return ResponseEntity.ok(challengeService.update(condition));
    }

    @Operation(summary = "챌린지 삭제", description = "챌린지 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "R0002", description = "챌린지 정보를 조회할 수 없습니다. 다시 확인해 주세요.",
                                    value = """
                                            {"code": "R0002", "message": "챌린지 정보를 조회할 수 없습니다. 다시 확인해 주세요."}
                                            """)
                    }, schema = @Schema(implementation = ErrorResponse.class)
            ))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ProgressResponse<Long>> delete(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(challengeService.delete(id));
    }
}
