package com.gogonew.api.mysql.domain.room;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import com.gogonew.api.mysql.domain.BaseTimeEntity;
import com.gogonew.api.mysql.domain.pocket.Pocket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Room extends BaseTimeEntity {
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)")
    @Id
    private UUID id;

    @Column
    private String roomName;

    @Column
    private String backgroundImgUrl;

    @Column
    private boolean disabled;

    @OneToMany(mappedBy = "room")
    private List<Pocket> pockets;
}
