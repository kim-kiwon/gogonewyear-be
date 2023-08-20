package com.gogonew.api.job.email;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.gogonew.api.common.BatchComponent;
import com.gogonew.api.email.EmailRepository;
import com.gogonew.api.job.JobTestUtils;
import com.gogonew.api.mysql.domain.goal.Goal;
import com.gogonew.api.mysql.domain.goal.GoalRepository;
import com.gogonew.api.mysql.domain.pocket.Pocket;
import com.gogonew.api.mysql.domain.pocket.PocketRepository;
import com.gogonew.api.mysql.domain.room.Room;
import com.gogonew.api.mysql.domain.room.RoomRepository;

@RunWith(SpringRunner.class)
@SpringBatchTest
@SpringBootTest
// @SpringBootTest(classes={PocketSendMailJobConfig.class, TestBatchConfig.class})
public class PocketSendMailJobConfigTest {
	private static final String JOB_NAME = PocketSendMailJobConfig.PREFIX + BatchComponent.JOB;

	@MockBean
	private EmailRepository emailRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private PocketRepository pocketRepository;

	@Autowired
	private GoalRepository goalRepository;

	@Autowired
	private JobTestUtils jobTestUtils;

	@Test
	public void 배치_수행결과_성공_테스트() throws Exception {
		//given
		JobLauncherTestUtils jobLauncherTestUtils = jobTestUtils.getJobTester(JOB_NAME);

		Room room = Room.builder().roomName("testRoom").backgroundImgUrl("http://www.naver.com").build();
		Pocket pocket = Pocket.builder().pocketName("testPocket").backgroundImgUrl("http://www.naver.com").room(room).build();
		Goal goal1 = Goal.builder().todo("testGoal1").pocket(pocket).build();
		Goal goal2 = Goal.builder().todo("testGoal2").pocket(pocket).build();

		roomRepository.save(room);
		pocketRepository.save(pocket);
		goalRepository.save(goal1);
		goalRepository.save(goal2);

		JobParameters jobParameters = new JobParametersBuilder()
			.addString("executeTime", String.valueOf(LocalDateTime.now()))
			.toJobParameters();

		// when
		JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

		// then
		assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
	}



}
