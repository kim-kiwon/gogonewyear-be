package com.gogonew.api.room;

import com.gogonew.api.mysql.domain.group.Room;
import com.gogonew.api.mysql.domain.group.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomDto.Response addRoom(RoomDto.Create request) {
        Room room = request.toEntity();
        roomRepository.save(room);

        return RoomDto.Response.of(room);
    }
}
