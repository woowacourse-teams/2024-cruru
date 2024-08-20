package com.cruru.choice.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.cruru.choice.domain.Choice;
import com.cruru.util.RepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("객관식 선택지 레포지토리 테스트")
class ChoiceRepositoryTest extends RepositoryTest {

    @Autowired
    private ChoiceRepository choiceRepository;

    @BeforeEach
    void setUp() {
        choiceRepository.deleteAllInBatch();
    }

    @DisplayName("이미 DB에 저장되어 있는 ID를 가진 선택지를 저장하면, 해당 ID의 선택지를 후에 작성된 정보로 업데이트한다.")
    @Test
    void sameIdUpdate() {
        //given
        Choice choice = new Choice("남자", 0, null);
        Choice saved = choiceRepository.save(choice);

        //when
        Choice updateChoice = new Choice(saved.getId(), "여자", 1, null);
        choiceRepository.save(updateChoice);

        //then
        Choice findChoice = choiceRepository.findById(saved.getId()).get();
        assertThat(findChoice.getContent()).isEqualTo("여자");
    }
}
