package com.gogonew.api.room;

import com.gogonew.api.core.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/v1/room")
    public ApiResponse createRoom(@RequestBody RoomDto.Create request) {
        return ApiResponse.success(roomService.addRoom(request));
    }
}
