package com.seoulmate.poppopseoul.domain.theme.controller;

import com.seoulmate.poppopseoul.common.dto.ProgressResponse;
import com.seoulmate.poppopseoul.common.enumeration.LanguageCode;
import com.seoulmate.poppopseoul.domain.theme.dto.ThemeCreateRequest;
import com.seoulmate.poppopseoul.domain.theme.dto.ThemeResponse;
import com.seoulmate.poppopseoul.domain.theme.service.ThemeService;
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

import java.util.List;

@Tag(name = "챌린지 테마 controller", description = "테마 관리 API 목록")
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
@RequestMapping("theme")
@RequiredArgsConstructor
public class ThemeController {

    private ThemeService themeService;

    @Operation(summary = "챌린지 테마 조회", description = "챌린지 테마 조회")
    @GetMapping
    public ResponseEntity<List<ThemeResponse>> getList(@RequestParam(value = "languageCode")LanguageCode languageCode) {
        return ResponseEntity.ok(themeService.getList(languageCode));
    }

    @Operation(summary = "챌린지 테마 등록", description = "챌린지 테마 등록")
    @PostMapping
    public ResponseEntity<ProgressResponse<ThemeResponse>> create(@RequestBody ThemeCreateRequest condition) {
        return ResponseEntity.ok(themeService.create(condition));
    }

    @Operation(summary = "챌린지 테마 삭제", description = "챌린지 테마 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "R0003", description = "챌린지 테마 정보를 조회할 수 없습니다. 다시 확인해 주세요.",
                                    value = """
                                            {"code": "R0003", "message": "테마 정보를 조회할 수 없습니다. 다시 확인해 주세요."}
                                            """)
                    }, schema = @Schema(implementation = ErrorResponse.class)
            ))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ProgressResponse<Long>> delete(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(themeService.delete(id));
    }

}
