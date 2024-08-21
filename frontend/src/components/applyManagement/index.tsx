import { useEffect, useRef } from 'react';
import { useParams } from 'react-router-dom';
import useApplyManagement from '@hooks/useApplyManagement';

import { HiOutlinePlusCircle } from 'react-icons/hi';
import QuestionBuilder from '@components/dashboard/DashboardCreate/Apply/QuestionBuilder';
import { APPLY_QUESTION_HEADER, DEFAULT_QUESTIONS, MAX_QUESTION_LENGTH } from '@constants/constants';

import S from './style';

export default function ApplyManagement({ isVisible }: { isVisible: boolean }) {
  const wrapperRef = useRef<HTMLDivElement>(null);
  const { postId } = useParams<{ postId: string }>() as {
    postId: string;
  };

  const {
    isLoading,
    applyState,
    addQuestion,
    setQuestionTitle,
    setQuestionType,
    setQuestionOptions,
    setQuestionRequiredToggle,
    setQuestionPrev,
    setQuestionNext,
    deleteQuestion,
  } = useApplyManagement({ postId });

  useEffect(() => {
    if (isVisible && wrapperRef.current && !isLoading) {
      wrapperRef.current.scrollTop = 0;
    }
  }, [isVisible, isLoading]);

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
        <button type="button">수정하기</button>
      </S.Section>
    </S.Wrapper>
  );
}
