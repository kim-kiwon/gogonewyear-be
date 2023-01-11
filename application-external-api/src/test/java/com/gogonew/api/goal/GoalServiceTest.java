package com.gogonew.api.goal;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gogonew.api.mysql.domain.goal.Goal;
import com.gogonew.api.mysql.domain.goal.GoalRepository;
import com.gogonew.api.mysql.domain.pocket.Pocket;

@ExtendWith(MockitoExtension.class)
public class GoalServiceTest {
	@Mock
	GoalRepository goalRepository;

	@InjectMocks
	GoalService goalService;

	@Test
	void 목표_단건_조회_성공Test() {
		// given
		UUID uuid = UUID.fromString("4a82febb-abe3-4db1-bb8c-dc6386a9db20");
		Goal goal = Goal.builder()
				.id(uuid)
				.todo("test목표")
				.succeed(false)
				.disabled(false)
				.pocket(new Pocket())
				.build();
		given(goalRepository.findById(uuid)).willReturn(Optional.ofNullable(goal));

		// when
		GoalDto.Response responseDto = goalService.getGoal();

		// then
		assertThat(responseDto.getId()).isEqualTo(uuid);
		assertThat(responseDto.getTodo()).isEqualTo("test목표");
		assertThat(responseDto.isSucceed()).isFalse();
		assertThat(responseDto.isDisabled()).isFalse();
	}

	@Test
	void 목표_단건_조회_실패Test() {

	}

	@Test
	void 목표_생성_성공Test() {

	}
}
