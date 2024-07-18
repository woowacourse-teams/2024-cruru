package com.cruru.chosenresponse.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.cruru.choice.domain.Choice;
import com.cruru.choice.domain.repository.ChoiceRepository;
import com.cruru.chosenresponse.domain.ChosenResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayName("지원자의 객관식 응답 레포지토리 테스트")
@DataJpaTest
class ChosenResponseRepositoryTest {

    @Autowired
    private ChosenResponseRepository chosenResponseRepository;

    @Autowired
    private ChoiceRepository choiceRepository;

    @AfterEach
    void tearDown() {
        chosenResponseRepository.deleteAllInBatch();
        choiceRepository.deleteAllInBatch();
    }

    @DisplayName("이미 DB에 저장되어 있는 ID를 가진 객관식 응답을 저장하면, 해당 ID의 객관식 응답은 후에 작성된 정보로 업데이트한다.")
    @Test
    void sameIdUpdate() {
        //given
        Choice choice = new Choice("남자", null);
        choiceRepository.save(choice);
        ChosenResponse chosenResponse = new ChosenResponse(null, null);
        ChosenResponse saved = chosenResponseRepository.save(chosenResponse);

        //when
        ChosenResponse updateChosenResponse = new ChosenResponse(chosenResponse.getId(), choice, null);
        chosenResponseRepository.save(updateChosenResponse);

        //then
        ChosenResponse foundChosenResponse = chosenResponseRepository.findById(saved.getId()).get();
        assertThat(foundChosenResponse.getChoice()).isNotNull();
    }

    @DisplayName("ID가 없는 객관식 응답을 저장하면, ID를 순차적으로 부여하여 저장한다.")
    @Test
    void saveNoId() {
        //given
        ChosenResponse chosenResponse1 = new ChosenResponse(null, null);
        ChosenResponse chosenResponse2 = new ChosenResponse(null, null);

        //when
        ChosenResponse savedChosenResponse1 = chosenResponseRepository.save(chosenResponse1);
        ChosenResponse savedChosenResponse2 = chosenResponseRepository.save(chosenResponse2);

        //then
        assertThat(savedChosenResponse1.getId() + 1).isEqualTo(savedChosenResponse2.getId());
    }
}
