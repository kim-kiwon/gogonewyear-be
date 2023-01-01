package com.gogonew.api.room;

import com.gogonew.api.mysql.domain.room.Room;
import com.gogonew.api.pocket.PocketDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

public class RoomDto {

    @Getter
    @NoArgsConstructor // Spring이 파라미터에서 생성하기 위해 필요
    public static class Create {
        @NotBlank(message = "방 제목을 작성해주세요.")
        @Size(max = 20, message = "방제목은 20자 이내로 작성해주세요.")
        private String roomName;

        @URL(message = "배경 이미지는 Url 양식으로 입력해주세요.")
        @NotBlank(message = "배경 이미지 URL을 입력해주세요.")
        @Size(max = 200, message = "배경 이미지 Url은 200자 이내로 작성해주세요.")
        private String backgroundImageUrl;

        // RoomCreateDto.Request -> Room
        public Room toEntity() {
            return Room.builder()
                .roomName(this.getRoomName())
                .backgroundImgUrl(this.getBackgroundImageUrl())
                .pockets(new ArrayList<>())
                .build();
        }
    }

    @Builder
    @Getter
    public static class Response {
        private String roomName;
        private String backgroundImageUrl;
        private boolean disabled;
        private List<PocketDto.Response> pockets;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;

        // Room -> RoomCreateDto.Response
        public static Response of(Room room) {
            return Response.builder()
                .roomName(room.getRoomName())
                .backgroundImageUrl(room.getBackgroundImgUrl())
                .disabled(room.isDisabled())
                .pockets(PocketDto.Response.ofList(room.getPockets())) // 순환참조 방지를 위해 Pocket 엔티티를 직접 반환하는게 아닌 Pocket.ResponseDto를 반환
                .createdDate(room.getCreatedDate())
                .modifiedDate(room.getModifiedDate())
                .build();
        }

        // List<Room>의 모든 원소를 of를 활용해 RoomDto.Response로 변환
        public static List<Response> ofList(List<Room> rooms) {
            return rooms.stream()
                .map(Response::of)
                .collect(Collectors.toList());
        }
    }
}
