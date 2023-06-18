package com.gogonew.api.room;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gogonew.api.core.exception.NoDataException;
import com.gogonew.api.mysql.domain.room.Room;
import com.gogonew.api.mysql.domain.room.RoomRepository;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {
	private static final UUID ROOM_ID = UUID.fromString("4a82febb-abe3-4db1-bb8c-dc6386a9db20");
	private static final UUID NOT_EXIST_ROOM_ID = UUID.fromString("40010ab0-a4ab-4c35-a7ee-1b14a200c529");

	@Mock
	private RoomRepository roomRepository;

	@InjectMocks
	private RoomService sut;

	@Test
	void 모든_방조회_성공Test() {
		// given
		Room room1 = Room.builder()
			.roomName("testRoom1")
			.backgroundImgUrl("http://www.testurl1.com")
			.disabled(false)
			.pockets(new ArrayList<>())
			.build();
		Room room2 = Room.builder()
			.roomName("testRoom2")
			.backgroundImgUrl("http://www.testurl2.com")
			.disabled(false)
			.pockets(new ArrayList<>())
			.build();

		when(roomRepository.findAll()).thenReturn(Arrays.asList(room1, room2));

		// when
		List<RoomDto.Response> responseDtos = sut.getAllRoom();

		// then
		assertThat(responseDtos.get(0).getRoomName()).isEqualTo("testRoom1");
		assertThat(responseDtos.get(0).getBackgroundImageUrl()).isEqualTo("http://www.testurl1.com");
		assertThat(responseDtos.get(1).getRoomName()).isEqualTo("testRoom2");
		assertThat(responseDtos.get(1).getBackgroundImageUrl()).isEqualTo("http://www.testurl2.com");
	}

	@Test
	void 특정_방_조회_성공Test() {
		// given
		Room room = Room.builder()
			.roomName("testRoom1")
			.backgroundImgUrl("http://www.testurl.com")
			.disabled(false)
			.pockets(new ArrayList<>())
			.build();
		when(roomRepository.findById(ROOM_ID)).thenReturn(Optional.ofNullable(room));

		// when
		RoomDto.Response responseDto = sut.getRoom(ROOM_ID);

		// then
		assertThat(responseDto.getRoomName()).isEqualTo("testRoom1");
		assertThat(responseDto.getBackgroundImageUrl()).isEqualTo("http://www.testurl.com");
	}

	@Test
	void 특정_방_조회_데이터_미존재시_실패Test() {
		// given
		when(roomRepository.findById(NOT_EXIST_ROOM_ID)).thenReturn(Optional.empty());

		// when/then
		assertThatThrownBy(() -> sut.getRoom(NOT_EXIST_ROOM_ID)).isInstanceOf(NoDataException.class);
	}

	@Test
	void 방_추가_성공Test() {
		// given
		RoomDto.Create createDto = new RoomDto.Create("testRoom1", "http://www.testurl.com");
		Room room = createDto.toEntity();
		given(roomRepository.save(any())).willReturn(room);

		// when
		RoomDto.Response responseDto = sut.addRoom(createDto);

		// then
		assertThat(responseDto.getRoomName()).isEqualTo("testRoom1");
		assertThat(responseDto.getBackgroundImageUrl()).isEqualTo("http://www.testurl.com");
	}
}