package com.seoulmate.poppopseoul.domain.attraction.controller;

import com.seoulmate.poppopseoul.domain.attraction.service.AttractionDataService;
import com.seoulmate.poppopseoul.exception.ErrorCode;
import com.seoulmate.poppopseoul.exception.ErrorResponse;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "서울시 공공 데이터 API", description = "서울시 공공 데이터 수집 API 목록")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
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
@RequestMapping("/seoul/data")
@RequiredArgsConstructor
public class AttractionDataController {

    private final AttractionDataService attractionDataService;

    @Operation(summary = "서울 공공 API 데이터 수집", description = "서울 공공 API 데이터 수집")
    @ApiErrorCodeExamples({ErrorCode.DATA_PROCESS_ERROR, ErrorCode.SEOUL_API_ERROR})
    @GetMapping
    public ResponseEntity<Void> getList() throws Exception {
        attractionDataService.saveSeoulAttractionData();
        return ResponseEntity.ok().build();
    }
}
