package com.gogonew.api.pocket;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import com.gogonew.api.goal.GoalDto;
import com.gogonew.api.mysql.domain.pocket.Pocket;
import com.gogonew.api.mysql.domain.room.Room;
import com.gogonew.api.validator.ValidUuid;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class PocketDto {

    @Getter
    @Setter
    @Schema(name = "PocketCreateDto")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Create {
        @ValidUuid(message = "roomId는 UUID 형태로 입력해주세요")
        @NotBlank(message = "roomId 필드를 입력해주세요")
        private String roomId;

        @NotBlank(message = "주머니 제목을 입력해주세요")
        @Size(max = 20, message = "주머니 제목은 20자 이내로 작성해주세요.")
        private String pocketName;

        @URL(message = "배경 이미지는 Url 양식으로 입력해주세요.")
        @NotBlank(message = "배경 이미지 URL을 입력해주세요.")
        @Size(max = 200, message = "배경 이미지 Url은 200자 이내로 작성해주세요.")
        private String backgroundImageUrl;

        @Email(message = "이메일 형식으로 입력해주세요")
        @NotBlank(message = "수신받을 이메일을 입력해주세요.")
        @Size(max = 100, message = "이메일은 100자 이내로 작성해주세요.")
        private String email;

        @Schema(hidden = true)
        private Room room;

        // PocketCreateDto.Request -> Room
        public Pocket toEntity() {
            return Pocket.builder()
                .pocketName(this.getPocketName())
                .backgroundImgUrl(this.getBackgroundImageUrl())
                .email(this.getEmail())
                .room(this.getRoom())
                .goals(new ArrayList<>())
                .build();
        }
    }

    @Builder
    @Getter
    @Schema(name = "PocketResponseDto")
    public static class Response {
        private UUID id;
        private String pocketName;
        private String backgroundImageUrl;
        private String email;
        private boolean disabled;
        private UUID roomId;
        private List<GoalDto.Response> goals;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;

        // Room -> RoomCreateDto.Response
        public static Response of(Pocket pocket) {
            return Response.builder()
                .id(pocket.getId())
                .pocketName(pocket.getPocketName())
                .backgroundImageUrl(pocket.getBackgroundImgUrl())
                .email(pocket.getEmail())
                .disabled(pocket.isDisabled())
                .roomId(pocket.getRoom().getId())
                .goals(GoalDto.Response.ofList(pocket.getGoals()))
                .createdDate(pocket.getCreatedDate())
                .modifiedDate(pocket.getModifiedDate())
                .build();
        }

        public static List<PocketDto.Response> ofList(List<Pocket> pockets) {
            return pockets.stream()
                .map(PocketDto.Response::of)
                .collect(Collectors.toList());
        }
    }
}
