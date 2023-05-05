package com.gogonew.api.room;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gogonew.api.core.exception.NoDataException;
import com.gogonew.api.mysql.domain.room.Room;
import com.gogonew.api.mysql.domain.room.RoomRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RoomService {
    private final RoomRepository roomRepository;

    @Transactional(readOnly = true)
    public List<RoomDto.Response> getAllRoom() {
        return RoomDto.Response.ofList(roomRepository.findAll());
    }

    @Transactional(readOnly = true)
    public RoomDto.Response getRoom(UUID roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new NoDataException("Room이 존재하지 않습니다. roomId = " + roomId));
        return RoomDto.Response.of(room);
    }

    @Transactional
    public RoomDto.Response addRoom(RoomDto.Create request) {
        Room room = request.toEntity();
        roomRepository.save(room);

        return RoomDto.Response.of(room);
    }
}
