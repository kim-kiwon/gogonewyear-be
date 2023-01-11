package com.gogonew.api.goal;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gogonew.api.core.exception.ApiException;
import com.gogonew.api.core.exception.ErrorCode;
import com.gogonew.api.mysql.domain.goal.Goal;
import com.gogonew.api.mysql.domain.goal.GoalRepository;
import com.gogonew.api.mysql.domain.pocket.Pocket;
import com.gogonew.api.mysql.domain.pocket.PocketRepository;
import com.gogonew.api.mysql.domain.room.Room;
import com.gogonew.api.mysql.domain.room.RoomRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GoalService {
	private final RoomRepository roomRepository;
	private final PocketRepository pocketRepository;
	private final GoalRepository goalRepository;



	@Transactional(readOnly = true)
	public GoalDto.Response getGoal(UUID roomId, UUID pocketId, GoalDto.Create request) {
		Goal goal = goalRepository.findById(goalId).orElseThrow(() ->
			new ApiException(ErrorCode.NO_DATA));

		return GoalDto.Response.of(goal);
	}

	@Transactional
	public GoalDto.Response createGoal(UUID roomId, UUID pocketId, GoalDto.Create request) {
		Room room = roomRepository.findById(roomId).orElseThrow(() ->
			new ApiException(ErrorCode.NO_DATA));
		Pocket pocket = pocketRepository.findById(pocketId).orElseThrow(() ->
			new ApiException(ErrorCode.NO_DATA));

		// 주머니가 해당 방에 존재하지 않는 경우
		if (pocket.getRoom().getId() != room.getId()) {
			throw new ApiException(ErrorCode.INVALID_INPUT_VALUE);
		}

		request.setPocket(pocket);
		Goal goal = request.toEntity();
		goalRepository.save(goal);

		return GoalDto.Response.of(goal);
	}
}
