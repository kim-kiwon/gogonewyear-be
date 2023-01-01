package com.gogonew.api.mysql.domain.room;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoomRepositoryTest {
    @Autowired
    private RoomRepository roomRepository;

    @Test
    void save() {
        //given
        Room room = Room.builder()
            .id(1L)
            .roomName("testRoom")
            .backgroundImgUrl("http://www.testurl.com")
            .disabled(false)
            .build();

        // when
        Room savedRoom = roomRepository.save(room);

        // then
        System.out.println(savedRoom);
    }
}