import { HiOutlinePlusCircle } from 'react-icons/hi';
import { Question, QuestionOptionValue } from '@customTypes/dashboard';
import Button from '@components/common/Button';
import ChevronButton from '@components/common/ChevronButton';
import QuestionBuilder from './QuestionBuilder';

import S from './style';

interface ApplyProps {
  applyState: Question[];
  addQuestion: () => void;
  setQuestionTitle: (index: number) => (title: string) => void;
  setQuestionType: (index: number) => (type: Question['type']) => void;
  setQuestionOptions: (index: number) => (Options: QuestionOptionValue[]) => void;
  setQuestionRequiredToggle: (index: number) => () => void;
  setQuestionPrev: (index: number) => () => void;
  setQuestionNext: (index: number) => () => void;
  deleteQuestion: (index: number) => void;
  prevStep: () => void;
  nextStep: () => void;
}

// 이름, 이메일, 전화번호의 3개 항목은 applyState에 언제나 기본으로 포함되어야 합니다.
const DEFAULT_QUESTION_LENGTH = 3;
const MAX_QUESTION_LENGTH = 20 + DEFAULT_QUESTION_LENGTH;
const DEFAULT_QUESTION: Question = {
  type: 'SHORT_ANSWER',
  question: '',
  choices: [],
  required: true,
};

export default function Apply({
  applyState,
  addQuestion,
  setQuestionTitle,
  setQuestionType,
  setQuestionOptions,
  setQuestionRequiredToggle,
  setQuestionPrev,
  setQuestionNext,
  deleteQuestion,
  prevStep,
  nextStep,
}: ApplyProps) {
  const choicesToRenderQuestion = applyState.length <= DEFAULT_QUESTION_LENGTH ? [DEFAULT_QUESTION] : applyState;

  return (
    <S.Wrapper>
      <S.Section>
        <S.SectionTitleContainer>
          <h2>지원자 정보</h2>
          <span>아래 항목은 모든 지원자들에게 기본적으로 제출받는 항목입니다.</span>
        </S.SectionTitleContainer>
        <S.DefaultInputItemsContainer>
          <S.DefaultInputItem>이름</S.DefaultInputItem>
          <S.DefaultInputItem>이메일</S.DefaultInputItem>
          <S.DefaultInputItem>전화번호</S.DefaultInputItem>
        </S.DefaultInputItemsContainer>
      </S.Section>

      <S.Section>
        <S.SectionTitleContainer>
          <h2>사전질문</h2>
          <span>지원자에게 질문하고 싶은 것이 있다면 입력해 주세요. (최대 20개)</span>
        </S.SectionTitleContainer>

        {choicesToRenderQuestion.map((question, index) => (
          // eslint-disable-next-line react/no-array-index-key
          <S.QuestionsContainer key={index}>
            <QuestionBuilder
              index={index}
              question={question}
              setQuestionTitle={setQuestionTitle}
              setQuestionType={setQuestionType}
              setQuestionOptions={setQuestionOptions}
              setQuestionPrev={setQuestionPrev}
              setQuestionNext={setQuestionNext}
              deleteQuestion={deleteQuestion}
              setQuestionRequiredToggle={setQuestionRequiredToggle}
            />
          </S.QuestionsContainer>
        ))}

        {applyState.length < MAX_QUESTION_LENGTH && (
          <S.AddQuestionButton
            type="button"
            onClick={addQuestion}
          >
            <HiOutlinePlusCircle />
            항목 추가
          </S.AddQuestionButton>
        )}
      </S.Section>

      <S.Section>
        <S.StepButtonsContainer>
          <Button
            disabled={false}
            onClick={prevStep}
            size="sm"
            color="white"
          >
            <S.ButtonContent>
              <ChevronButton
                direction="left"
                size="sm"
              />
              이전
            </S.ButtonContent>
          </Button>
          <Button
            disabled={false}
            onClick={nextStep}
            size="sm"
            color="white"
          >
            <S.ButtonContent>
              다음
              <ChevronButton
                direction="right"
                size="sm"
              />
            </S.ButtonContent>
          </Button>
        </S.StepButtonsContainer>
      </S.Section>
    </S.Wrapper>
  );
}
