package com.gogonew.api.goal;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gogonew.api.core.response.ApiMessage;
import com.gogonew.api.pocket.PocketDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "pocket", description = "목표를 생성/조회 합니다.")
@Slf4j
@RequiredArgsConstructor
@RestController
public class GoalController {
	private final GoalService goalService;

	@Operation(summary = "해당 주머니의 모든 목표 조회", description = "해당 주머니의 모든 목표를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "ok",
			content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiMessage.class))}),
		@ApiResponse(responseCode = "400", description = "client error", content = @Content),
		@ApiResponse(responseCode = "404", description = "not found", content = @Content),
		@ApiResponse(responseCode = "500", description = "server error", content = @Content)})
	@GetMapping("/v1/{roomId}/pocket/{pocketId}")
	public ApiMessage getAllPocket(
		@Parameter(description = "조회할 주머니Id") @PathVariable UUID pocketId) {
		return ApiMessage.success(pocketService.getAllPocket(roomId));
	}

	@Operation(summary = "해당 주머니의 특정 목표 조회", description = "해당 주머니의 특정 목표를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "ok",
			content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiMessage.class))}),
		@ApiResponse(responseCode = "400", description = "client error", content = @Content),
		@ApiResponse(responseCode = "404", description = "not found", content = @Content),
		@ApiResponse(responseCode = "500", description = "server error", content = @Content)})
	@GetMapping("/v1/{roomId}/pocket/{pocketId}/goal/{goalId}")
	public ApiMessage getGoal(
		@Parameter(description = "조회할 roomId") @PathVariable UUID roomId,
		@Parameter(description = "조회할 pocketId") @PathVariable UUID pocketId,
		@Parameter(description = "조회할 goalId") @PathVariable UUID goalId) {
		return ApiMessage.success(goalService.getGoal(roomId, pocketId, goalId));
	}

	@Operation(summary = "주머니 생성", description = "신규 주머니를 생성합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "ok",
			content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiMessage.class))}),
		@ApiResponse(responseCode = "400", description = "client error", content = @Content),
		@ApiResponse(responseCode = "404", description = "not found", content = @Content),
		@ApiResponse(responseCode = "500", description = "server error", content = @Content)})
	@PostMapping("/v1/{roomId}/pocket")
	public ApiMessage createPocket(
		@Parameter(description = "주머니 추가할 roomId") @PathVariable UUID roomId,
		@Parameter(description = "입력 데이터") @RequestBody @Valid PocketDto.Create request) {
		return ApiMessage.success(pocketService.createPocket(roomId, request));
	}
}
