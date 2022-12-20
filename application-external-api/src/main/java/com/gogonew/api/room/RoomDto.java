package com.gogonew.api.room;

import com.gogonew.api.mysql.domain.group.Room;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RoomDto {

    @Getter
    @NoArgsConstructor // Spring이 파라미터에서 생성하기 위해 필요
    public static class Create {
        private String roomName;
        private String backgroundImageUrl;

        // RoomCreateDto.Request -> Room
        public Room toEntity() {
            return Room.builder()
                .roomName(this.getRoomName())
                .backgroundImgUrl(this.getBackgroundImageUrl())
                .build();

        }
    }

    @Builder
    @Getter
    public static class Response {
        private String roomName;
        private String backgroundImageUrl;
        private boolean isDeleted;
        private LocalDateTime createdDate;
        private LocalDateTime modifiedDate;

        // Room -> RoomCreateDto.Response
        public static Response of(Room room) {
            return Response.builder()
                .roomName(room.getRoomName())
                .backgroundImageUrl(room.getBackgroundImgUrl())
                .isDeleted(room.isDeleted())
                .createdDate(room.getCreatedDate())
                .modifiedDate(room.getModifiedDate())
                .build();
        }
    }
}
