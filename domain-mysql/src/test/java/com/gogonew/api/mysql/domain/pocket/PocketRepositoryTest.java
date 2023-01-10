package com.gogonew.api.mysql.domain.pocket;

import static org.assertj.core.api.Assertions.*;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PocketRepositoryTest {
	@Autowired
	private PocketRepository pocketRepository;

	@Test
	@DisplayName("저장 O")
	void save() {
		//given
		Pocket pocket = Pocket.builder()
			.pocketName("testPocket")
			.backgroundImgUrl("http://www.naver.com")
			.email("test@naver.com")
			.disabled(false)
			.build();

		// when
		Pocket savedPocket = pocketRepository.save(pocket);

		// then
		assertThat(savedPocket.getId()).isInstanceOf(UUID.class);
		assertThat(savedPocket.getPocketName()).isEqualTo(pocket.getPocketName());
		assertThat(savedPocket.getBackgroundImgUrl()).isEqualTo(pocket.getBackgroundImgUrl());
		assertThat(savedPocket.getEmail()).isEqualTo(pocket.getEmail());
		assertThat(savedPocket.isDisabled()).isEqualTo(pocket.isDisabled());
	}

	@Test
	@DisplayName("Id 기반 조회 O")
	void findById() {
		//given
		Pocket pocket = Pocket.builder()
			.pocketName("testPocket")
			.backgroundImgUrl("http://www.naver.com")
			.email("test@naver.com")
			.disabled(false)
			.build();
		Pocket savedPocket = pocketRepository.save(pocket);


		// when
		Pocket findPocket = pocketRepository.findById(savedPocket.getId())
			.orElseThrow(() -> new NoSuchElementException("해당 데이터가 존재하지 않습니다."));

		// then
		assertThat(findPocket.getId()).isInstanceOf(UUID.class);
		assertThat(findPocket.getPocketName()).isEqualTo(pocket.getPocketName());
		assertThat(findPocket.getBackgroundImgUrl()).isEqualTo(pocket.getBackgroundImgUrl());
		assertThat(findPocket.getEmail()).isEqualTo(pocket.getEmail());
		assertThat(findPocket.isDisabled()).isEqualTo(pocket.isDisabled());
	}
}