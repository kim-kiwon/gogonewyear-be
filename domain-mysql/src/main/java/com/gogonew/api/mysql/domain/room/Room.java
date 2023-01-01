package com.gogonew.api.mysql.domain.room;

import com.gogonew.api.mysql.domain.BaseTimeEntity;
import com.gogonew.api.mysql.domain.pocket.Pocket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Room extends BaseTimeEntity {
    @GeneratedValue(generator = "UUID") // 분산 DB 고려 identity 보다 UUID 선택
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)") // 컬럼타입을 해당 타입으로 하지않으면 공백이 들어가며 제대로 수행X
    @Id
    private UUID id;

    @Column
    private String roomName;

    @Column
    private String backgroundImgUrl;

    @Column
    private boolean disabled;

    @OneToMany(mappedBy = "room")
    private List<Pocket> pockets = new ArrayList<>();
}
