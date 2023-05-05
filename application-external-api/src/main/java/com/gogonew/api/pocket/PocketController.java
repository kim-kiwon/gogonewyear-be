package com.gogonew.api.pocket;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gogonew.api.core.response.ApiResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "pocket", description = "주머니를 생성/조회 합니다.")
@Slf4j
@RequiredArgsConstructor
@RestController
public class PocketController {

    private final PocketService pocketService;

    @Operation(summary = "모든 주머니 조회", description = "모든 주머니를 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "ok",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiResult.class))}),
        @ApiResponse(responseCode = "400", description = "client error", content = @Content),
        @ApiResponse(responseCode = "404", description = "not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "server error", content = @Content)})
    @GetMapping("/v1/pocket")
    public ApiResult<List<PocketDto.Response>> getAllPocket() {
        return ApiResult.success(pocketService.getAllPocket());
    }

    @Operation(summary = "주머니 단건 조회", description = "해당 Id의 주머니를 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "ok",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiResult.class))}),
        @ApiResponse(responseCode = "400", description = "client error", content = @Content),
        @ApiResponse(responseCode = "404", description = "not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "server error", content = @Content)})
    @GetMapping("/v1/pocket/{pocketId}")
    public ApiResult<PocketDto.Response> getPocket(
        @Parameter(description = "조회할 주머니의 Id") @PathVariable UUID pocketId) {
        return ApiResult.success(pocketService.getPocket(pocketId));
    }

    @Operation(summary = "주머니 생성", description = "신규 주머니를 생성합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "ok",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiResult.class))}),
        @ApiResponse(responseCode = "400", description = "client error", content = @Content),
        @ApiResponse(responseCode = "404", description = "not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "server error", content = @Content)})
    @PostMapping("/v1/pocket")
    public ApiResult<PocketDto.Response> createPocket(
            @Parameter(description = "입력 데이터") @RequestBody @Valid PocketDto.Create request) {
        return ApiResult.success(pocketService.createPocket(request));
    }
}
