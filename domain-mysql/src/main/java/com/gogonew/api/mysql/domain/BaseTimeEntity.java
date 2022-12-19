package com.gogonew.api.mysql.domain;

import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass // Jpa Entity들이 BaseEntity 클래스 상속시 필드 자동 추가
@EntityListeners(AuditingEntityListener.class) // BaseTimeEntity에 Auditing 기능 추가
public class BaseTimeEntity {
    @CreatedDate // 생성시 날짜 자동 생성
    private LocalDateTime createdDate;

    @LastModifiedDate // 수정시 날짜 자동 갱신
    private LocalDateTime modifiedDate;
}
