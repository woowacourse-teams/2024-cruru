import { useState } from 'react';

import { Question, QuestionControlActionType, QuestionOptionValue } from '@customTypes/dashboard';

import InputField from '@components/_common/molecules/InputField';
import Dropdown from '@components/_common/molecules/Dropdown';
import ToggleSwitch from '@components/_common/atoms/ToggleSwitch';
import { QUESTION_TYPE_NAME } from '@constants/constants';

import type { DropdownItemType } from '@components/_common/molecules/DropdownItemRenderer';
import DropdownItemRenderer from '@components/_common/molecules/DropdownItemRenderer';
import CheckBoxField from '@components/recruitment/CheckBoxField';
import RadioInputField from '@components/recruitment/RadioInputField';
import QuestionController from '../QuestionController';

import S from './style';

interface QuestionBuilderProps {
  index: number;
  question: Question;
  setQuestionTitle: (index: number) => (title: string) => void;
  setQuestionType: (index: number) => (type: Question['type']) => void;
  setQuestionOptions: (index: number) => (Options: QuestionOptionValue[]) => void;
  setQuestionRequiredToggle: (index: number) => () => void;
  setQuestionPrev: (index: number) => () => void;
  setQuestionNext: (index: number) => () => void;
  deleteQuestion: (index: number) => void;
}

export default function QuestionBuilder({
  index,
  question,
  setQuestionTitle,
  setQuestionType,
  setQuestionOptions,
  setQuestionRequiredToggle,
  setQuestionPrev,
  setQuestionNext,
  deleteQuestion,
}: QuestionBuilderProps) {
  const [title, setTitle] = useState<string>(question?.question || '');
  const [currentQuestionType, setCurrentQuestionType] = useState<Question['type']>(question?.type || 'SHORT_ANSWER');
  const [isRequired, setIsRequired] = useState<boolean>(question ? question.required : true);

  const handleChangeTitle = (event: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(event.target.value);
    setQuestionTitle(index)(event.target.value);
  };

  const handleChangeQuestionType = (type: Question['type']) => {
    if (type === currentQuestionType) return;

    setCurrentQuestionType(type);
    setQuestionType(index)(type);
  };

  const handleUpdateQuestionChoices = (newChoices: QuestionOptionValue[]) => {
    setQuestionOptions(index)(newChoices);
  };

  const toggleIsRequired = () => {
    setIsRequired(!isRequired);
    setQuestionRequiredToggle(index)();
  };

  const handleClickControlButton = (actionType: QuestionControlActionType) => {
    if (actionType === 'moveUp') setQuestionPrev(index)();
    if (actionType === 'moveDown') setQuestionNext(index)();
    if (actionType === 'delete') deleteQuestion(index);
  };

  const QUESTION_TYPES: DropdownItemType[] = [
    { id: 1, name: QUESTION_TYPE_NAME.SHORT_ANSWER, onClick: () => handleChangeQuestionType('SHORT_ANSWER') },
    { id: 2, name: QUESTION_TYPE_NAME.LONG_ANSWER, onClick: () => handleChangeQuestionType('LONG_ANSWER') },
    { id: 3, name: QUESTION_TYPE_NAME.SINGLE_CHOICE, onClick: () => handleChangeQuestionType('SINGLE_CHOICE') },
    { id: 4, name: QUESTION_TYPE_NAME.MULTIPLE_CHOICE, onClick: () => handleChangeQuestionType('MULTIPLE_CHOICE') },
  ].map((value) => ({ type: 'clickable', ...value }));

  return (
    <S.Wrapper>
      <S.QuestionBuilderContainer>
        <S.InputBox>
          <InputField
            type="text"
            placeholder="질문을 입력하세요."
            onChange={handleChangeTitle}
            value={title}
            required
          />
          <Dropdown
            initValue={QUESTION_TYPE_NAME[currentQuestionType]}
            size="sm"
            width={200}
            isShadow={false}
          >
            <DropdownItemRenderer items={QUESTION_TYPES} />
          </Dropdown>
        </S.InputBox>

        {currentQuestionType === 'SINGLE_CHOICE' && (
          <RadioInputField
            choices={question.choices}
            setChoices={handleUpdateQuestionChoices}
          />
        )}

        {currentQuestionType === 'MULTIPLE_CHOICE' && (
          <CheckBoxField
            choices={question.choices}
            setChoices={handleUpdateQuestionChoices}
          />
        )}

        <S.RequiredBox>
          {isRequired ? '이 질문은 필수 질문입니다.' : '이 질문은 선택 질문입니다.'}
          <ToggleSwitch
            width="4rem"
            isChecked={isRequired}
            isDisabled={false}
            onChange={toggleIsRequired}
          />
        </S.RequiredBox>
      </S.QuestionBuilderContainer>
      <S.ControlButtonContainer>
        <QuestionController onClickControlButton={handleClickControlButton} />
      </S.ControlButtonContainer>
    </S.Wrapper>
  );
}
