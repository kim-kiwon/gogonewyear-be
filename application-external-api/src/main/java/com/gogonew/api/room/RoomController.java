package com.gogonew.api.room;

import com.gogonew.api.core.exception.ApiException;
import com.gogonew.api.core.exception.ErrorCode;
import com.gogonew.api.core.response.ApiResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/v1/room")
    public ApiResponse createRoom(
            @RequestBody @Valid RoomDto.Create request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 로깅하고 에러응답
            throw new ApiException(ErrorCode.INVALID_INPUT_VALUE);
        }

        return ApiResponse.success(roomService.addRoom(request));
    }
}
