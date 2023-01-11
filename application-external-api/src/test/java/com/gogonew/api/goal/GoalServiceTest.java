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

import com.gogonew.api.core.exception.ApiException;
import com.gogonew.api.mysql.domain.goal.Goal;
import com.gogonew.api.mysql.domain.goal.GoalRepository;
import com.gogonew.api.mysql.domain.pocket.Pocket;
import com.gogonew.api.mysql.domain.pocket.PocketRepository;

@ExtendWith(MockitoExtension.class)
public class GoalServiceTest {
	@Mock
	GoalRepository goalRepository;

	@Mock
	PocketRepository pocketRepository;

	@InjectMocks
	GoalService goalService;

	@Test
	void 목표단건조회_성공Test() {
		// given
		UUID uuid = UUID.randomUUID();
		Goal goal = Goal.builder()
			.id(uuid)
			.todo("test목표")
			.succeed(false)
			.disabled(false)
			.pocket(new Pocket())
			.build();
		given(goalRepository.findById(uuid)).willReturn(Optional.ofNullable(goal));

		// when
		GoalDto.Response responseDto = goalService.getGoal(uuid);

		// then
		assertThat(responseDto.getId()).isEqualTo(uuid);
		assertThat(responseDto.getTodo()).isEqualTo("test목표");
		assertThat(responseDto.isSucceed()).isFalse();
		assertThat(responseDto.isDisabled()).isFalse();
	}

	@Test
	void 목표단건조회_UUID해당데이터없을경우_실패Test() {
		// given
		UUID badUuid = UUID.randomUUID();
		given(goalRepository.findById(badUuid)).willReturn(Optional.empty());

		// when/then
		assertThatThrownBy(() -> goalService.getGoal(badUuid)).isInstanceOf(ApiException.class);
	}
}
