package com.gogonew.api.pocket;

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
import com.gogonew.api.mysql.domain.pocket.Pocket;
import com.gogonew.api.mysql.domain.pocket.PocketRepository;
import com.gogonew.api.mysql.domain.room.Room;
import com.gogonew.api.mysql.domain.room.RoomRepository;

@ExtendWith(MockitoExtension.class)
class PocketServiceTest {
	private static final UUID ROOM_ID = UUID.fromString("4a82febb-abe3-4db1-bb8c-dc6386a9db20");
	private static final UUID POCKET_ID = UUID.fromString("9aafcc1d-a4a0-428c-98a8-370b21e70bb4");
	private static final UUID NOT_EXIST_POCKET_ID = UUID.fromString("fc75d5cc-950a-4ea9-9cf4-026c568de1ac");

	@Mock
	private RoomRepository roomRepository;

	@Mock
	private PocketRepository pocketRepository;

	@InjectMocks
	private PocketService sut;

	@Test
	void 모든_포켓_조회_성공Test() {
		// given
		Room room1 = Room.builder()
			.id(ROOM_ID)
			.roomName("testRoom1")
			.backgroundImgUrl("http://www.testurl1.com")
			.disabled(false)
			.pockets(new ArrayList<>())
			.build();
		Pocket pocket1 = Pocket.builder()
			.pocketName("testPocket1")
			.backgroundImgUrl("http://www.testurl1.com")
			.email("testEmail@naver.com")
			.room(room1)
			.goals(new ArrayList<>())
			.build();
		Pocket pocket2 = Pocket.builder()
			.pocketName("testPocket2")
			.backgroundImgUrl("http://www.testurl2.com")
			.email("testEmail@naver.com")
			.room(room1)
			.goals(new ArrayList<>())
			.build();
		when(pocketRepository.findAll()).thenReturn(Arrays.asList(pocket1, pocket2));

		// when
		List<PocketDto.Response> responseDtos = sut.getAllPocket();

		// then
		assertThat(responseDtos.get(0).getPocketName()).isEqualTo("testPocket1");
		assertThat(responseDtos.get(0).getBackgroundImageUrl()).isEqualTo("http://www.testurl1.com");
		assertThat(responseDtos.get(0).getEmail()).isEqualTo("testEmail@naver.com");
		assertThat(responseDtos.get(0).getRoomId()).isEqualTo(ROOM_ID);
		assertThat(responseDtos.get(1).getPocketName()).isEqualTo("testPocket2");
		assertThat(responseDtos.get(1).getBackgroundImageUrl()).isEqualTo("http://www.testurl2.com");
		assertThat(responseDtos.get(1).getEmail()).isEqualTo("testEmail@naver.com");
		assertThat(responseDtos.get(1).getRoomId()).isEqualTo(ROOM_ID);
	}

	@Test
	void 특정_포켓_조회_성공Test() {
		// given
		Room room1 = Room.builder()
			.id(ROOM_ID)
			.roomName("testRoom1")
			.backgroundImgUrl("http://www.testurl1.com")
			.disabled(false)
			.pockets(new ArrayList<>())
			.build();
		Pocket pocket1 = Pocket.builder()
			.id(POCKET_ID)
			.pocketName("testPocket1")
			.backgroundImgUrl("http://www.testurl1.com")
			.email("testEmail@naver.com")
			.room(room1)
			.goals(new ArrayList<>())
			.build();
		when(pocketRepository.findById(POCKET_ID)).thenReturn(Optional.ofNullable(pocket1));

		// when
		PocketDto.Response responseDto = sut.getPocket(POCKET_ID);

		// then
		assertThat(responseDto.getPocketName()).isEqualTo("testPocket1");
		assertThat(responseDto.getBackgroundImageUrl()).isEqualTo("http://www.testurl1.com");
		assertThat(responseDto.getEmail()).isEqualTo("testEmail@naver.com");
		assertThat(responseDto.getRoomId()).isEqualTo(ROOM_ID);
	}

	@Test
	void 특정_포켓_조회_데이터_미존재시_실패Test() {
		// given
		when(pocketRepository.findById(NOT_EXIST_POCKET_ID)).thenReturn(Optional.empty());

		// when/then
		assertThatThrownBy(() -> sut.getPocket(NOT_EXIST_POCKET_ID)).isInstanceOf(NoDataException.class);
	}

	@Test
	void 포켓_추가_성공Test() {
		// given
		Room room1 = Room.builder()
			.id(ROOM_ID)
			.roomName("testRoom1")
			.backgroundImgUrl("http://www.testurl1.com")
			.disabled(false)
			.pockets(new ArrayList<>())
			.build();

		PocketDto.Create createDto = PocketDto.Create.builder()
			.pocketName("testPocket1")
			.backgroundImageUrl("http://www.testurl1.com")
			.email("testEmail@naver.com")
			.roomId(ROOM_ID.toString())
			.build();

		Pocket createPocket = createDto.toEntity();

		when(roomRepository.findById(ROOM_ID)).thenReturn(Optional.ofNullable(room1));
		when(pocketRepository.save(any(Pocket.class))).thenReturn(createPocket);

		// when
		PocketDto.Response responseDto = sut.createPocket(createDto);

		// then
		assertThat(responseDto.getPocketName()).isEqualTo("testPocket1");
		assertThat(responseDto.getBackgroundImageUrl()).isEqualTo("http://www.testurl1.com");
		assertThat(responseDto.getEmail()).isEqualTo("testEmail@naver.com");
		assertThat(responseDto.getRoomId()).isEqualTo(ROOM_ID);
	}
}