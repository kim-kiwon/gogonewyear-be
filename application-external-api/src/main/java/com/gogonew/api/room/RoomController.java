package com.gogonew.api.room;

import com.gogonew.api.core.response.ApiMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "room", description = "방을 생성/조회 합니다.")
@Slf4j
@RequiredArgsConstructor
@RestController
public class RoomController {

    private final RoomService roomService;

    @Operation(summary = "모든 방 조회", description = "모든 방을 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "ok",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiMessage.class))}),
        @ApiResponse(responseCode = "400", description = "client error", content = @Content),
        @ApiResponse(responseCode = "404", description = "not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "server error", content = @Content)})
    @GetMapping("/v1/room")
    public ApiMessage getAllRoom() {
        return ApiMessage.success(roomService.getAllRoom());
    }

    @Operation(summary = "단일 방 조회", description = "roomId를 통해 방을 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "ok",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiMessage.class))}),
        @ApiResponse(responseCode = "400", description = "client error", content = @Content),
        @ApiResponse(responseCode = "404", description = "not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "server error", content = @Content)})
    @GetMapping("/v1/room/{roomId}")
    public ApiMessage getRoom(
        @Parameter(description = "랜덤으로 부여되는 방의 Id") @PathVariable String roomId) {
        return ApiMessage.success(roomService.getRoom(UUID.fromString(roomId)));
    }


    @Operation(summary = "방 생성", description = "신규 방을 생성합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "ok",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiMessage.class))}),
        @ApiResponse(responseCode = "400", description = "client error", content = @Content),
        @ApiResponse(responseCode = "404", description = "not found", content = @Content),
        @ApiResponse(responseCode = "500", description = "server error", content = @Content)})
    @PostMapping("/v1/room")
    public ApiMessage createRoom(
            @Parameter(description = "입력 데이터") @RequestBody @Valid RoomDto.Create request) {
        return ApiMessage.success(roomService.addRoom(request));
    }
}
