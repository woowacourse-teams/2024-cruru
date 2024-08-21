import { HiOutlinePlusCircle } from 'react-icons/hi';
import { Question, QuestionOptionValue } from '@customTypes/dashboard';
import { APPLY_QUESTION_HEADER, DEFAULT_QUESTIONS, MAX_QUESTION_LENGTH } from '@constants/constants';

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
  const isNextBtnValid =
    applyState.length === DEFAULT_QUESTIONS.length ||
    applyState
      .slice(DEFAULT_QUESTIONS.length)
      .every((question) => question.question.trim() && question.choices.length !== 1);

  return (
    <S.Wrapper>
      <S.Section>
        <S.SectionTitleContainer>
          <h2>{APPLY_QUESTION_HEADER.defaultQuestions.title}</h2>
          <span>{APPLY_QUESTION_HEADER.defaultQuestions.description}</span>
        </S.SectionTitleContainer>
        <S.DefaultInputItemsContainer>
          <S.DefaultInputItem>이름</S.DefaultInputItem>
          <S.DefaultInputItem>이메일</S.DefaultInputItem>
          <S.DefaultInputItem>전화번호</S.DefaultInputItem>
        </S.DefaultInputItemsContainer>
      </S.Section>

      <S.Section>
        <S.SectionTitleContainer>
          <h2>{APPLY_QUESTION_HEADER.addQuestion.title}</h2>
          <span>{APPLY_QUESTION_HEADER.addQuestion.description}</span>
        </S.SectionTitleContainer>

        {applyState.map((question, index) => {
          if (index >= 3) {
            return (
              <S.QuestionsContainer key={question.id}>
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
            );
          }
          return null;
        })}

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
            disabled={!isNextBtnValid}
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
