package com.gogonew.api.goal;

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

@Tag(name = "goal", description = "목표를 생성/조회 합니다.")
@Slf4j
@RequiredArgsConstructor
@RestController
public class GoalController {
	private final GoalService goalService;

	@Operation(summary = "모든 목표 조회", description = "모든 목표를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "ok",
			content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiResult.class))}),
		@ApiResponse(responseCode = "400", description = "client error", content = @Content),
		@ApiResponse(responseCode = "404", description = "not found", content = @Content),
		@ApiResponse(responseCode = "500", description = "server error", content = @Content)})
	@GetMapping("/v1/goal")
	public ApiResult<List<GoalDto.Response>> getAllGoal() {
		return ApiResult.success(goalService.getAllGoal());
	}

	@Operation(summary = "목표 단건 조회", description = "해당 Id의 목표를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "ok",
			content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiResult.class))}),
		@ApiResponse(responseCode = "400", description = "client error", content = @Content),
		@ApiResponse(responseCode = "404", description = "not found", content = @Content),
		@ApiResponse(responseCode = "500", description = "server error", content = @Content)})
	@GetMapping("/v1/goal/{goalId}")
	public ApiResult<GoalDto.Response> getGoal(
		@Parameter(description = "조회할 goalId") @PathVariable UUID goalId) {
		return ApiResult.success(goalService.getGoal(goalId));
	}

	@Operation(summary = "목표 생성", description = "신규 목표를 생성합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "ok",
			content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiResult.class))}),
		@ApiResponse(responseCode = "400", description = "client error", content = @Content),
		@ApiResponse(responseCode = "404", description = "not found", content = @Content),
		@ApiResponse(responseCode = "500", description = "server error", content = @Content)})
	@PostMapping("/v1/goal")
	public ApiResult<GoalDto.Response> createGoal(
		@Parameter(description = "입력 데이터") @RequestBody @Valid GoalDto.Create request) {
		return ApiResult.success(goalService.createGoal(request));
	}
}
