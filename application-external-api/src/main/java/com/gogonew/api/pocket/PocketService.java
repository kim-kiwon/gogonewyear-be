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
    public List<Response> getAllPocket() {
        return PocketDto.Response.ofList(pocketRepository.findAll());
    }

    @Transactional(readOnly = true)
    public PocketDto.Response getPocket(UUID pocketId) {
        Pocket pocket = pocketRepository.findById(pocketId).orElseThrow(() -> new ApiException(ErrorCode.NO_DATA));
        return PocketDto.Response.of(pocket);
    }

    @Transactional
    public PocketDto.Response createPocket(PocketDto.Create request) {
        UUID roomId = UUID.fromString(request.getRoomId()); // 검증을 위해 String으로 받고. UUID로 변환.
        Room room = roomRepository.findById(roomId).orElseThrow(() ->
            new ApiException(ErrorCode.NO_DATA));

        request.setRoom(room); // 연관관계 주인인 Pocket에서 JPA 연관관계 매핑

        Pocket pocket = request.toEntity();
        pocketRepository.save(pocket);

        return PocketDto.Response.of(pocket);
    }

}
