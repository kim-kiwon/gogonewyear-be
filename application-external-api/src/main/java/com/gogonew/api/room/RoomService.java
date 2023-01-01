package com.gogonew.api.room;

import com.gogonew.api.core.exception.ApiException;
import com.gogonew.api.core.exception.ErrorCode;
import com.gogonew.api.mysql.domain.room.Room;
import com.gogonew.api.mysql.domain.room.RoomRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RoomService {
    private final RoomRepository roomRepository;

    @Transactional(readOnly = true)
    public List<RoomDto.Response> getAllRoom() {
        return RoomDto.Response.ofList(roomRepository.findAll());
    }

    @Transactional(readOnly = true)
    public RoomDto.Response getRoom(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ApiException(ErrorCode.NO_DATA));
        return RoomDto.Response.of(room);
    }

    @Transactional
    public RoomDto.Response addRoom(RoomDto.Create request) {
        Room room = request.toEntity();
        roomRepository.save(room);

        return RoomDto.Response.of(room);
    }
}
