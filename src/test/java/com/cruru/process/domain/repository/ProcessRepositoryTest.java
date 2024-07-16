package com.cruru.process.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.cruru.process.domain.Process;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayName("프로세스 레포지토리 테스트")
@DataJpaTest
class ProcessRepositoryTest {

    @Autowired
    private ProcessRepository processRepository;

    @DisplayName("이미 DB에 저장되어 있는 ID를 가진 프로세스를 저장하면, 해당 ID의 프로세스는 후에 작성된 정보로 업데이트한다.")
    @Test
    void sameIdUpdate() {
        //given
        Process process = new Process(0, "면접", "화상 면접", null);
        Process saved = processRepository.save(process);

        //when
        Process updatedProcess = new Process(saved.getId(), 1, "새로운 면접", "대면 면접", null);
        processRepository.save(updatedProcess);

        //then
        Process findProcess = processRepository.findById(saved.getId()).get();
        assertThat(findProcess.getSequence()).isEqualTo(1);
        assertThat(findProcess.getName()).isEqualTo("새로운 면접");
        assertThat(findProcess.getDescription()).isEqualTo("대면 면접");
    }

    @DisplayName("ID가 없는 프로세스를 저장하면, 순차적으로 ID가 부여하여 저장된다.")
    @Test
    void saveNoId() {
        //given
        Process process1 = new Process(0, "면접", "화상 면접", null);
        Process process2 = new Process(1, "새로운 면접", "대면 면접", null);

        //when
        Process savedProcess1 = processRepository.save(process1);
        Process savedProcess2 = processRepository.save(process2);

        //then
        assertThat(savedProcess1.getId() + 1).isEqualTo(savedProcess2.getId());
    }
}
