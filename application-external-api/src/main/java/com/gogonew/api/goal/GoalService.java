package com.gogonew.api.goal;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gogonew.api.core.exception.ApiException;
import com.gogonew.api.core.exception.ErrorCode;
import com.gogonew.api.mysql.domain.goal.Goal;
import com.gogonew.api.mysql.domain.goal.GoalRepository;
import com.gogonew.api.mysql.domain.pocket.Pocket;
import com.gogonew.api.mysql.domain.pocket.PocketRepository;
import com.gogonew.api.mysql.domain.room.RoomRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GoalService {
	private final RoomRepository roomRepository;
	private final PocketRepository pocketRepository;
	private final GoalRepository goalRepository;

	@Transactional(readOnly = true)
	public List<GoalDto.Response> getAllGoal() {
		return GoalDto.Response.ofList(goalRepository.findAll());
	}

	@Transactional(readOnly = true)
	public GoalDto.Response getGoal(UUID goalId) {
		Goal goal = goalRepository.findById(goalId).orElseThrow(() ->
			new ApiException(ErrorCode.NO_DATA));

		return GoalDto.Response.of(goal);
	}

	@Transactional
	public GoalDto.Response createGoal(GoalDto.Create request) {
		UUID pocketId = UUID.fromString(request.getPocketId());
		Pocket pocket = pocketRepository.findById(pocketId).orElseThrow(() ->
			new ApiException(ErrorCode.NO_DATA));

		request.setPocket(pocket);

		Goal goal = request.toEntity();
		goalRepository.save(goal);

		return GoalDto.Response.of(goal);
	}
}
