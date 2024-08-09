import { useEffect, useRef } from 'react';

import { QuestionOptionValue } from '@customTypes/dashboard';
import { HiOutlineDocument, HiX } from 'react-icons/hi';
import S from './style';

interface QuestionChoicesBuilderProps {
  choices: QuestionOptionValue[];
  onUpdate: (choices: QuestionOptionValue[]) => void;
}

export default function QuestionChoicesBuilder({ choices, onUpdate }: QuestionChoicesBuilderProps) {
  const inputRefs = useRef<(HTMLInputElement | null)[]>([]);

  useEffect(() => {
    inputRefs.current = inputRefs.current.slice(0, choices.length);
  }, [choices]);

  const setInputRef = (el: HTMLInputElement | null, index: number) => {
    inputRefs.current[index] = el;
  };

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>, index: number) => {
    const newValue = event.target.value;
    const newChoices = [...choices];

    if (index < newChoices.length) {
      newChoices[index].value = newValue;
    } else {
      newChoices.push({ value: newValue });
    }

    onUpdate(newChoices);
  };

  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>, index: number) => {
    if (event.key === 'Enter') {
      event.preventDefault();
      if (index === choices.length - 1) {
        onUpdate([...choices, { value: '' }]);
        setTimeout(() => inputRefs.current[index + 1]?.focus(), 0);
      } else {
        inputRefs.current[index + 1]?.focus();
      }
    }
  };

  const handleDeleteChoice = (deleteIndex: number) => {
    const newChoices = choices.filter((_, index) => index !== deleteIndex);
    onUpdate(newChoices);
  };

  const choicesToRender = choices.length === 0 ? [{ value: '' }] : choices;

  return (
    <S.Wrapper>
      {choicesToRender.map((choice, index) => (
        // eslint-disable-next-line react/no-array-index-key
        <S.ChoiceContainer key={index}>
          <S.ChoiceInputGroup>
            <HiOutlineDocument />
            <S.ChoiceInput
              ref={(el) => setInputRef(el, index)}
              type="text"
              placeholder="옵션을 입력하세요."
              value={choice.value}
              onChange={(event) => handleInputChange(event, index)}
              onKeyDown={(event) => handleKeyDown(event, index)}
            />
          </S.ChoiceInputGroup>
          <S.DeleteButton
            type="button"
            onClick={() => handleDeleteChoice(index)}
          >
            <HiX />
          </S.DeleteButton>
        </S.ChoiceContainer>
      ))}
    </S.Wrapper>
  );
}
