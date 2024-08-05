import React, { useCallback, useEffect, useRef } from 'react';

import S from './style';
import RadioInputOption from '../RadioInputOption';

interface Option {
  value: string;
}

interface Props {
  options: Option[];
  setOptions: React.Dispatch<React.SetStateAction<Option[]>>;
}

export default function RadioInputField({ options, setOptions }: Props) {
  const inputRefs = useRef<(HTMLInputElement | null)[]>([]);

  const handleOptionChange = (index: number, value: string) => {
    const newOptions = [...options];
    newOptions[index].value = value;
    setOptions(newOptions);
  };

  const addOption = () => {
    setOptions([...options, { value: '' }]);
  };

  const deleteOption = (index: number) => {
    const newOptions = options.slice();
    newOptions.splice(index, 1);
    setOptions(newOptions);
  };

  const handleOptionBlur = (index: number) => {
    const isLastOption = index === options.length - 1;
    const isEmptyValue = options[index].value.trim() === '';
    if (!isLastOption && isEmptyValue) {
      deleteOption(index);
    }
    if (isLastOption && !isEmptyValue) {
      addOption();
    }
  };

  const focusLastOption = useCallback(() => {
    inputRefs.current[options.length - 1]?.focus();
  }, [options.length]);

  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>, index: number) => {
    if ((e.key === 'Tab' || e.key === 'Enter') && !e.shiftKey) {
      e.preventDefault();
      if (index === options.length - 1) {
        addOption();
      }
      focusLastOption();
    }
  };

  useEffect(() => {
    focusLastOption();
  }, [options.length, focusLastOption]);

  const setInputRefCallback = (index: number) => (node: HTMLInputElement) => {
    inputRefs.current[index] = node;
  };

  return (
    <S.Container>
      {options.map((option, index) => (
        <RadioInputOption
          // eslint-disable-next-line react/no-array-index-key
          key={index}
          isDisabled={false}
          isDeleteBtn={options.length - 1 !== index}
          onDeleteBtnClick={() => deleteOption(index)}
          inputAttrs={{
            value: option.value,
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
