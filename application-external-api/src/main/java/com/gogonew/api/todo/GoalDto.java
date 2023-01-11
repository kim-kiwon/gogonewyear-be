package com.gogonew.api.todo;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.gogonew.api.mysql.domain.goal.Goal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class GoalDto {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Create {
        @NotBlank(message = "목표를 작성해주세요.")
        @Size(max = 100, message = "목표는 100자 이내로 작성해주세요.")
        private String todo;

        // GoalCreateDto -> Goal
        public Goal toEntity() {
            return Goal.builder()
                .todo(this.getTodo())
                .disabled(false)
                .succeed(false)
                .build();
        }
    }

    @Builder
    @Getter
    public static class Response {
        private UUID id;
        private String todo;
        private boolean succeed;
        private boolean disabled;
        private UUID pocketId;

        // Goal -> GoalResponseDto
        public static Response of(Goal goal) {
            return Response.builder()
                .id(goal.getId())
                .todo(goal.getTodo())
                .succeed(goal.isSucceed())
                .disabled(goal.isDisabled())
                .pocketId(goal.getPocket().getId())
                .build();
        }
    }
}
