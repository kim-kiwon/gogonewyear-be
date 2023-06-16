package com.gogonew.api.goal;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.gogonew.api.mysql.domain.goal.Goal;
import com.gogonew.api.mysql.domain.pocket.Pocket;
import com.gogonew.api.validator.ValidUuid;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class GoalDto {

    @Getter
    @Setter
    @Schema(name = "GoalCreateDto")
    @NoArgsConstructor
    public static class Create {
        @ValidUuid
        @NotBlank(message = "주머니의 Id를 입력해주세요.")
        private String pocketId;
        @NotBlank(message = "목표를 작성해주세요.")
        @Size(max = 100, message = "목표는 100자 이내로 작성해주세요.")
        private String todo;
        @Schema(hidden = true)
        private Pocket pocket;

        // GoalCreateDto -> Goal
        public Goal toEntity() {
            return Goal.builder()
                .todo(this.getTodo())
                .disabled(false)
                .succeed(false)
                .pocket(this.getPocket())
                .build();
        }
    }

    @Getter
    @Setter
    @Schema(name = "GoalCreateBulkDto")
    @NoArgsConstructor
    public static class CreateBulk {
        @ValidUuid
        @NotBlank(message = "주머니의 Id를 입력해주세요.")
        private String pocketId;
        @Valid
        List<GoalInfo> goalInfos;
        @Schema(hidden = true)
        private Pocket pocket;

        // CreateBulk -> Goal
        public List<Goal> toEntity() {
            List<Goal> entities = new ArrayList<>();
            for (GoalInfo goalInfo : goalInfos) {
                Goal goal = Goal.builder()
                    .todo(goalInfo.getTodo())
                    .disabled(false)
                    .succeed(false)
                    .pocket(this.getPocket())
                    .build();
                entities.add(goal);
            }
            return entities;
        }
    }

    @Builder
    @Getter
    @Schema(name = "GoalResponseDto")
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

        public static List<GoalDto.Response> ofList(List<Goal> goals) {
            return goals.stream()
                .map(GoalDto.Response::of)
                .collect(Collectors.toList());
        }
    }

    @Getter
    private static class GoalInfo {
        @NotBlank(message = "목표를 작성해주세요.")
        @Size(max = 100, message = "목표는 100자 이내로 작성해주세요.")
        private String todo;
    }
}
