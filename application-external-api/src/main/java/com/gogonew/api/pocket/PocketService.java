package com.gogonew.api.pocket;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gogonew.api.core.exception.ApiException;
import com.gogonew.api.core.exception.ErrorCode;
import com.gogonew.api.mysql.domain.pocket.Pocket;
import com.gogonew.api.mysql.domain.pocket.PocketRepository;
import com.gogonew.api.mysql.domain.room.Room;
import com.gogonew.api.mysql.domain.room.RoomRepository;
import com.gogonew.api.pocket.PocketDto.Response;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PocketService {
    private final PocketRepository pocketRepository;
    private final RoomRepository roomRepository;

    @Transactional(readOnly = true)
    public List<Response> getAllPocket(UUID roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() ->
            new ApiException(ErrorCode.NO_DATA));

        return PocketDto.Response.ofList(room.getPockets());
    }

    @Transactional
    public PocketDto.Response createPocket(UUID roomId, PocketDto.Create request) {
        Room room = roomRepository.findById(roomId).orElseThrow(() ->
            new ApiException(ErrorCode.NO_DATA));

        request.setRoom(room);

        Pocket pocket = request.toEntity();
        pocketRepository.save(pocket);

        return PocketDto.Response.of(pocket);
    }

    @Transactional(readOnly = true)
    public PocketDto.Response getPocket(UUID roomId, UUID pocketId) {
        Pocket pocket = pocketRepository.findById(pocketId).orElseThrow(() -> new ApiException(ErrorCode.NO_DATA));
        if (!pocket.roomIdEquals(roomId)) {
            log.error("조회 요청한 주머니가 해당 방에 존재하지 않습니다. ParamRoomId: {}, DBRoomId: {}", roomId, pocket.getRoom().getId());
            throw new ApiException(ErrorCode.INVALID_INPUT_VALUE);
        }
        return PocketDto.Response.of(pocket);
    }


}
