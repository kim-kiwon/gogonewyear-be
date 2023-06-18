package com.gogonew.api.goal;

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
import com.gogonew.api.mysql.domain.goal.Goal;
import com.gogonew.api.mysql.domain.goal.GoalRepository;
import com.gogonew.api.mysql.domain.pocket.Pocket;
import com.gogonew.api.mysql.domain.pocket.PocketRepository;

@ExtendWith(MockitoExtension.class)
public class GoalServiceTest {

	private static final UUID ROOM_ID = UUID.fromString("4a82febb-abe3-4db1-bb8c-dc6386a9db20");
	private static final UUID POCKET_ID = UUID.fromString("9aafcc1d-a4a0-428c-98a8-370b21e70bb4");
	private static final UUID GOAL_ID = UUID.fromString("88804712-264c-42f2-9fd6-6de637cd1131");
	private static final UUID NOT_EXIST_GOAL_ID = UUID.fromString("88804712-264c-42f2-9fd6-6de637cd1131");

	@Mock
	private PocketRepository pocketRepository;

	@Mock
	private GoalRepository goalRepository;

	@InjectMocks
	private GoalService sut;

	@Test
	void 모든_목표_조회_성공Test() {
		// given
		Pocket pocket1 = Pocket.builder()
			.id(POCKET_ID)
			.pocketName("testPocket1")
			.backgroundImgUrl("http://www.testurl1.com")
			.email("testEmail@naver.com")
			.goals(new ArrayList<>())
			.build();
		Goal goal1 = Goal.builder()
			.todo("testGoal1")
			.pocket(pocket1)
			.build();
		Goal goal2 = Goal.builder()
			.todo("testGoal2")
			.pocket(pocket1)
			.build();

		when(goalRepository.findAll()).thenReturn(Arrays.asList(goal1, goal2));

		// when
		List<GoalDto.Response> responseDtos = sut.getAllGoal();

		// then
		assertThat(responseDtos.get(0).getPocketId()).isEqualTo(POCKET_ID);
		assertThat(responseDtos.get(0).getTodo()).isEqualTo("testGoal1");
		assertThat(responseDtos.get(1).getPocketId()).isEqualTo(POCKET_ID);
		assertThat(responseDtos.get(1).getTodo()).isEqualTo("testGoal2");
	}

	@Test
	void 특정_목표_조회_성공Test() {
		// given
		Goal goal = Goal.builder()
			.id(GOAL_ID)
			.todo("testGoal1")
			.succeed(false)
			.disabled(false)
			.pocket(new Pocket())
			.build();
		when(goalRepository.findById(GOAL_ID)).thenReturn(Optional.ofNullable(goal));

		// when
		GoalDto.Response responseDto = sut.getGoal(GOAL_ID);

		// then
		assertThat(responseDto.getId()).isEqualTo(GOAL_ID);
		assertThat(responseDto.getTodo()).isEqualTo("testGoal1");
		assertThat(responseDto.isSucceed()).isFalse();
		assertThat(responseDto.isDisabled()).isFalse();
	}

	@Test
	void 특정_목표_조회_데이터_미존재시_실패Test() {
		// given
		when(goalRepository.findById(NOT_EXIST_GOAL_ID)).thenReturn(Optional.empty());

		// when/then
		assertThatThrownBy(() -> sut.getGoal(NOT_EXIST_GOAL_ID)).isInstanceOf(NoDataException.class);
	}

	@Test
	void 목표_추가_성공Test() {
		// given
		Pocket pocket1 = Pocket.builder()
			.id(POCKET_ID)
			.pocketName("testPocket1")
			.backgroundImgUrl("http://www.testurl1.com")
			.email("testEmail@naver.com")
			.goals(new ArrayList<>())
			.build();

		GoalDto.Create createDto = GoalDto.Create.builder()
			.pocketId(POCKET_ID.toString())
			.todo("testGoal1")
			.build();


		Goal createGoal = createDto.toEntity();

		when(pocketRepository.findById(POCKET_ID)).thenReturn(Optional.ofNullable(pocket1));
		when(goalRepository.save(any(Goal.class))).thenReturn(createGoal);

		// when
		GoalDto.Response responseDto = sut.createGoal(createDto);

		// then
		assertThat(responseDto.getTodo()).isEqualTo("testGoal1");
		assertThat(responseDto.isSucceed()).isFalse();
		assertThat(responseDto.isDisabled()).isFalse();
	}

	@Test
	void 목표_벌크_추가_성공Test() {
		// given
		Pocket pocket1 = Pocket.builder()
			.id(POCKET_ID)
			.pocketName("testPocket1")
			.backgroundImgUrl("http://www.testurl1.com")
			.email("testEmail@naver.com")
			.goals(new ArrayList<>())
			.build();

		GoalDto.CreateBulk createBulkDto = GoalDto.CreateBulk.builder()
			.pocketId(POCKET_ID.toString())
			.todos(List.of("testGoal1", "testGoal2"))
			.build();

		List<Goal> createGoals = createBulkDto.toEntity();

		when(pocketRepository.findById(POCKET_ID)).thenReturn(Optional.ofNullable(pocket1));
		when(goalRepository.saveAll(anyList())).thenReturn(createGoals);

		// when
		List<GoalDto.Response> responseDtos = sut.createGoalBulk(createBulkDto);

		// then
		assertThat(responseDtos.get(0).getPocketId()).isEqualTo(POCKET_ID);
		assertThat(responseDtos.get(0).getTodo()).isEqualTo("testGoal1");
		assertThat(responseDtos.get(1).getPocketId()).isEqualTo(POCKET_ID);
		assertThat(responseDtos.get(1).getTodo()).isEqualTo("testGoal2");
	}
}
