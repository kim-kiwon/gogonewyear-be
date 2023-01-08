package com.gogonew.api.mysql.domain.room;

import static org.assertj.core.api.Assertions.*;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest // JPA 필요 빈 주입 + InMemoryDB 사용 + @Transactional로 트랜잭션과 수행후 롤백 보장
class RoomRepositoryTest {
    @Autowired
    private RoomRepository roomRepository;

   @Test
   @DisplayName("저장 O")
   void save() {
       //given
       Room room = Room.builder()
           .roomName("testRoom")
           .backgroundImgUrl("http://www.testurl.com")
           .disabled(false)
           .build();

       // when
       Room savedRoom = roomRepository.save(room);

       // then
       assertThat(savedRoom.getId()).isInstanceOf(UUID.class);
       assertThat(savedRoom.getRoomName()).isEqualTo(room.getRoomName());
       assertThat(savedRoom.getBackgroundImgUrl()).isEqualTo(room.getBackgroundImgUrl());
       assertThat(savedRoom.isDisabled()).isEqualTo(room.isDisabled());
   }

    @Test
    @DisplayName("Id 기반 조회 O")
    void findById() {
        //given
        Room room = Room.builder()
            .roomName("testRoom")
            .backgroundImgUrl("http://www.testurl.com")
            .disabled(false)
            .build();
        Room savedRoom = roomRepository.save(room);

        // when
        Room findRoom = roomRepository.findById(savedRoom.getId())
            .orElseThrow(() -> new NoSuchElementException("해당 데이터가 존재하지 않습니다."));

        // then
        assertThat(findRoom.getId()).isInstanceOf(UUID.class);
        assertThat(findRoom.getRoomName()).isEqualTo(room.getRoomName());
        assertThat(findRoom.getBackgroundImgUrl()).isEqualTo(room.getBackgroundImgUrl());
        assertThat(findRoom.isDisabled()).isEqualTo(room.isDisabled());
    }
}