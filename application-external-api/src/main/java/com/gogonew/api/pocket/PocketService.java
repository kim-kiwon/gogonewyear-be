package com.gogonew.api.pocket;

import com.gogonew.api.core.exception.ApiException;
import com.gogonew.api.core.exception.ErrorCode;
import com.gogonew.api.mysql.domain.pocket.Pocket;
import com.gogonew.api.mysql.domain.pocket.PocketRepository;
import com.gogonew.api.mysql.domain.room.Room;
import com.gogonew.api.mysql.domain.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PocketService {
    private final PocketRepository pocketRepository;
    private final RoomRepository roomRepository;

    @Transactional
    public PocketDto.Response createPocket(Long roomId, PocketDto.Create request) {
        Room room = roomRepository.findById(roomId).orElseThrow(() ->
            new ApiException(ErrorCode.NO_DATA));

        request.setRoom(room);

        Pocket pocket = request.toEntity();
        pocketRepository.save(pocket);

        return PocketDto.Response.of(pocket);
    }
}
