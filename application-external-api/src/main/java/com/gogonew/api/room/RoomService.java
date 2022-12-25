package com.gogonew.api.room;

import com.gogonew.api.core.exception.ApiException;
import com.gogonew.api.core.exception.ErrorCode;
import com.gogonew.api.mysql.domain.group.Room;
import com.gogonew.api.mysql.domain.group.RoomRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public List<RoomDto.Response> getAllRoom() {
        return RoomDto.Response.ofList(roomRepository.findAll());
    }

    public RoomDto.Response getRoom(UUID roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ApiException(ErrorCode.NO_DATA));
        return RoomDto.Response.of(room);
    }

    public RoomDto.Response addRoom(RoomDto.Create request) {
        Room room = request.toEntity();
        roomRepository.save(room);

        return RoomDto.Response.of(room);
    }
}
