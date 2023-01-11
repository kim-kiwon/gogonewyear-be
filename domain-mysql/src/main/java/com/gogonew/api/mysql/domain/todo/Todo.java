package com.gogonew.api.mysql.domain.todo;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
public class Todo extends BaseTimeEntity {
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(columnDefinition = "BINARY(16)")
	@Id
	private UUID id;

	@Column
	private String message;

	@Column
	private boolean succeed;

	@Column
	private boolean disabled;

	@ManyToOne
	@JoinColumn(name = "_id")
	private Pocket pocket;
}
