package com.gogonew.api.pocket;

import com.gogonew.api.core.exception.ApiException;
import com.gogonew.api.core.exception.ErrorCode;
import com.gogonew.api.mysql.domain.pocket.Pocket;
import com.gogonew.api.mysql.domain.pocket.PocketRepository;
import com.gogonew.api.mysql.domain.room.Room;
import com.gogonew.api.mysql.domain.room.RoomRepository;
import com.gogonew.api.pocket.PocketDto.Response;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PocketService {
    private final PocketRepository pocketRepository;
    private final RoomRepository roomRepository;

    @Transactional(readOnly = true)
    public List<Response> getAllPocket(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(() ->
            new ApiException(ErrorCode.NO_DATA));

        return PocketDto.Response.ofList(room.getPockets());
    }

    @Transactional
    public PocketDto.Response createPocket(Long roomId, PocketDto.Create request) {
        Room room = roomRepository.findById(roomId).orElseThrow(() ->
            new ApiException(ErrorCode.NO_DATA));

        request.setRoom(room);

        Pocket pocket = request.toEntity();
        pocketRepository.save(pocket);

        return PocketDto.Response.of(pocket);
    }

    @Transactional(readOnly = true)
    public PocketDto.Response getPocket(Long roomId, Long pocketId) {
        Pocket pocket = pocketRepository.findById(pocketId).orElseThrow(() -> new ApiException(ErrorCode.NO_DATA));
        if (pocket.getRoom().getId() != roomId) {
            log.error("주머니 조회의 파라미터 roomId와. 조회한 주머니의 roomId가 다릅니다.");
            throw new ApiException(ErrorCode.INVALID_INPUT_VALUE);
        }
        return PocketDto.Response.of(pocket);
    }


}
