package com.gogonew.api.mysql.domain.pocket;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.gogonew.api.mysql.domain.BaseTimeEntity;
import com.gogonew.api.mysql.domain.room.Room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pocket extends BaseTimeEntity {
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)")
    @Id
    private UUID id;

    @Column
    private String pocketName;

    @Column
    private String backgroundImgUrl;

    @Column
    private String email;

    @Column
    private boolean disabled;

    @ManyToOne
    @JoinColumn(name = "_id")
    private Room room;

    public boolean roomIdEquals(UUID roomId) {
        return this.getRoom()
            .getId()
            .equals(roomId);
    }
}
