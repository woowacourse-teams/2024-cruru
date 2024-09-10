import { useEffect, useRef } from 'react';
import { useParams } from 'react-router-dom';

import useApplyManagement from '@hooks/useApplyManagement';
import Button from '@components/common/Button';
import QuestionBuilder from '@components/dashboard/DashboardCreate/Apply/QuestionBuilder';
import { APPLY_QUESTION_HEADER, DEFAULT_QUESTIONS, MAX_QUESTION_LENGTH } from '@constants/constants';

import { HiOutlinePlusCircle } from 'react-icons/hi';
import S from './style';

export default function ApplyManagement({ isVisible }: { isVisible: boolean }) {
  const wrapperRef = useRef<HTMLDivElement>(null);
  const { applyFormId } = useParams<{ applyFormId: string }>() as {
    applyFormId: string;
  };

  const {
    isLoading,
    applyState,
    modifyApplyQuestionsMutator,
    addQuestion,
    setQuestionTitle,
    setQuestionType,
    setQuestionOptions,
    setQuestionRequiredToggle,
    setQuestionPrev,
    setQuestionNext,
    deleteQuestion,
  } = useApplyManagement({ applyFormId });

  useEffect(() => {
    if (isVisible && wrapperRef.current && !isLoading) {
      wrapperRef.current.scrollTop = 0;
    }
  }, [isVisible, isLoading]);

  if (isLoading) {
    <div>로딩 중입니다...</div>;
  }

  const isNextBtnValid =
    applyState.length === DEFAULT_QUESTIONS.length ||
    applyState
      .slice(DEFAULT_QUESTIONS.length)
      .every((question) => question.question.trim() && question.choices.length !== 1);

  const handleSubmit = (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();
    modifyApplyQuestionsMutator.mutate();
  };

  return (
    <S.Wrapper ref={wrapperRef}>
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
          if (index >= DEFAULT_QUESTIONS.length) {
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
        <S.ModifyButtonContainer>
          <Button
            type="button"
            color="primary"
            size="fillContainer"
            disabled={!isNextBtnValid}
            onClick={handleSubmit}
          >
            수정하기
          </Button>
        </S.ModifyButtonContainer>
      </S.Section>
    </S.Wrapper>
  );
}
