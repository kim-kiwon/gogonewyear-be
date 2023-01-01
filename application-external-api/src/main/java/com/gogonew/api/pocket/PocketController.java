package com.gogonew.api.pocket;

import com.gogonew.api.core.response.ApiMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "room", description = "방을 생성/조회 합니다.")
@Slf4j
@RequiredArgsConstructor
@RestController
public class PocketController {

    private final PocketService pocketService;

    @Operation(summary = "주머니 생성", description = "신규 주머니를 생성합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "ok",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiMessage.class))}),
        @ApiResponse(responseCode = "400", description = "client error", content = @Content),
        @ApiResponse(responseCode = "404", description = "not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "server error", content = @Content)})
    @PostMapping("/v1/{roomId}/pocket")
    public ApiMessage createPocket(
            @Parameter(description = "주머니 추가할 roomId") @PathVariable Long roomId,
            @Parameter(description = "입력 데이터") @RequestBody @Valid PocketDto.Create request) {
        return ApiMessage.success(pocketService.createPocket(roomId, request));
    }
}