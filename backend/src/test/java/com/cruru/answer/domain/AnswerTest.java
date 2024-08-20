package com.cruru.answer.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.cruru.answer.exception.AnswerContentLengthException;
import com.cruru.question.domain.Question;
import com.cruru.util.fixture.QuestionFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("답변 도메인 테스트")
class AnswerTest {

    @DisplayName("질문 형식이 단답형인 경우, 답변 내용의 길이가 50자를 초과하면 예외가 발생한다.")
    @Test
    void createShortAnswer_invalidContentLength() {
        // given
        String invalidContent = "ThisTextIsGreaterThan50CharactersSoAnExceptionOccurs";
        Question question = QuestionFixture.shortAnswerType(null);
    
        // when&then
        assertThatThrownBy(() -> new Answer(invalidContent, question, null))
                .isInstanceOf(AnswerContentLengthException.class);
    }

    @DisplayName("질문 형식이 장문형인 경우, 답변 내용의 길이가 1,000자를 초과하면 예외가 발생한다.")
    @Test
    void createLongAnswer_invalidContentLength() {
        // given
        String invalidContent = """
                                안녕하세요. 저는 최냥인입니다. 크루루에 지원하게 된 이유는 이 동아리가 제가 찾고 있던 열정과 배움의 장을 제공해줄 수 있다고 생각했기 때문입니다. 
                                저는 새로운 분야에 도전하는 것을 두려워하지 않으며, 다양한 경험을 통해 스스로를 성장시키는 것을 목표로 하고 있습니다. 
                                특히, 크루루가 지향하는 목표와 활동 방향이 저의 관심사와 잘 맞아떨어진다고 느껴 지원을 결심하게 되었습니다.              
                                저는 학업과 다양한 활동을 병행하면서 꾸준히 자기 계발을 해왔습니다. 중고등학교 시절에는 독서 토론 동아리에서 활동하며, 
                                논리적으로 사고하고 자신의 생각을 설득력 있게 표현하는 법을 배웠습니다. 이 경험을 통해 협업의 중요성과 팀워크를 배웠고, 
                                이를 바탕으로 타인의 의견을 존중하면서도 내 생각을 명확히 전달하는 능력을 키울 수 있었습니다. 
                                이런 점에서 크루루의 활동들이 저와 잘 맞을 것이라 생각합니다. 제가 크루루에서 가장 기대하는 부분은 다양한 사람들과의 교류입니다. 
                                저는 다양한 배경을 가진 사람들과의 소통을 통해 폭넓은 시각을 갖추는 것이 중요하다고 생각합니다. 
                                프로젝트, 세미나 등 같은 활동을 통해 서로 다른 경험과 생각을 공유하며, 함께 성장해 나가는 과정이 매우 의미 있을 것이라 확신합니다.   
                                또한, 저는 책임감을 가지고 맡은 바를 성실히 수행하는 사람입니다. 이전 학기에는 학과에서 팀 프로젝트의 조장을 맡아 팀원 간의 의견을 조율하고, 
                                각자의 역할을 명확히 분담하여 프로젝트를 성공적으로 마무리한 경험이 있습니다. 이 과정에서 배운 리더십과 협업 능력을 크루루에서도 발휘해, 
                                동아리 활동에 기여하고 싶습니다. 마지막으로, 크루루에서 저는 배움에 대한 열정과 적극적인 자세를 가지고 임할 것입니다. 
                                동아리 활동을 통해 새로운 지식을 습득하고, 동아리 구성원들과 함께 목표를 이루어 나가는 과정에서 더욱 성장하고 싶습니다. 
                                저는 크루루에서 얻은 경험을 바탕으로 학업뿐만 아니라, 다양한 분야에서 의미 있는 성과를 이루고자 합니다.
                                저의 열정과 성실함이 크루루에 긍정적인 영향을 미칠 수 있기를 바랍니다. 주어진 기회를 통해 동아리의 발전에 기여할 뿐만 아니라, 
                                저 스스로도 더 큰 성장을 이뤄내고 싶습니다. 감사합니다.
                                """;
        Question question = QuestionFixture.longAnswerType(null);

        // when&then
        assertThatThrownBy(() -> new Answer(invalidContent, question, null))
                .isInstanceOf(AnswerContentLengthException.class);
    }
}
