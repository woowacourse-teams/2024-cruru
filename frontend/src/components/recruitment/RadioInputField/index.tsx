import React, { useCallback, useEffect, useRef } from 'react';
import { QuestionOptionValue } from '@customTypes/dashboard';

import S from './style';
import RadioInputOption from '../../_common/molecules/RadioInputOption';

interface ChoiceOption {
  choice: string;
}

interface Props {
  choices: ChoiceOption[];
  setChoices: (newChoices: QuestionOptionValue[]) => void;
}

export default function RadioInputField({ choices, setChoices }: Props) {
  const inputRefs = useRef<(HTMLInputElement | null)[]>([]);

  const handleOptionChange = (index: number, value: string) => {
    const newOptions = [...choices];
    newOptions[index].choice = value;
    setChoices(newOptions);
  };

  const addOption = () => {
    setChoices([...choices, { choice: '' }]);
  };

  const deleteOption = (index: number) => {
    const newOptions = choices.slice();
    newOptions.splice(index, 1);
    setChoices(newOptions);
  };

  const handleOptionBlur = (index: number) => {
    const isLastOption = index === choices.length - 1;
    const isEmptyValue = choices[index].choice.trim() === '';
    if (!isLastOption && isEmptyValue) {
      deleteOption(index);
    }
    if (isLastOption && !isEmptyValue) {
      addOption();
    }
  };

  const focusLastOption = useCallback(() => {
    inputRefs.current[choices.length - 1]?.focus();
  }, [choices.length]);

  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>, index: number) => {
    if (e.nativeEvent.isComposing) return;

    if ((e.key === 'Tab' || e.key === 'Enter') && !e.shiftKey) {
      e.preventDefault();
      if (index === choices.length - 1) {
        addOption();
      }
    }
  };

  useEffect(() => {
    focusLastOption();
  }, [focusLastOption]);

  const setInputRefCallback = (index: number) => (node: HTMLInputElement) => {
    inputRefs.current[index] = node;
  };

  return (
    <S.Container>
      {choices.map((choice, index) => (
        <RadioInputOption
          // eslint-disable-next-line react/no-array-index-key
          key={index}
          isDisabled={false}
          isDeleteBtn={choices.length - 1 !== index}
          onDeleteBtnClick={() => deleteOption(index)}
          inputAttrs={{
            value: choice.choice,
            ref: setInputRefCallback(index),
            onChange: (e) => handleOptionChange(index, e.target.value),
            onKeyDown: (e) => handleKeyDown(e, index),
            onBlur: () => handleOptionBlur(index),
          }}
        />
      ))}
    </S.Container>
  );
}
