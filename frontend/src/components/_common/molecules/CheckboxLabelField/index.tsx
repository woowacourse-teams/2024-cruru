import { useId } from 'react';

import HiddenElementForSR from '@components/_common/atoms/ScreenReaderHidden';
import CheckBox from '../../atoms/CheckBox';
import S from './style';

interface Option {
  optionLabel: string;
  isChecked: boolean;
  onToggle: (isChecked: boolean) => void;
  name?: string;
}

interface CheckboxLabelField {
  label?: string;
  description?: string;
  disabled?: boolean;
  error?: string;
  required?: boolean;
  options: Option[];
}

export default function CheckboxLabelField({
  label,
  description,
  disabled = false,
  error,
  required = false,
  options,
}: CheckboxLabelField) {
  const id = useId();

  return (
    <S.Wrapper>
      <S.HeadWrapper>
        <S.HeadWrapper>
          {label && (
            <S.LabelWrapper>
              <S.Label
                htmlFor={id}
                disabled={!!disabled}
              >
                {label}
              </S.Label>
              {required && <S.Asterisk />}
              {required && <HiddenElementForSR>필수 질문입니다.</HiddenElementForSR>}
            </S.LabelWrapper>
          )}
          {description && <S.DescriptionWrapper>{description}</S.DescriptionWrapper>}
        </S.HeadWrapper>
      </S.HeadWrapper>

      <S.OptionsWrapper>
        {options.map(({ name, optionLabel, isChecked, onToggle }, index) => (
          // eslint-disable-next-line react/no-array-index-key
          <S.Option key={index}>
            <CheckBox
              id={id}
              isChecked={isChecked}
              onToggle={onToggle}
              isDisabled={disabled}
              name={name}
            />
            <S.OptionLabel>{optionLabel}</S.OptionLabel>
          </S.Option>
        ))}
      </S.OptionsWrapper>

      {error && <S.ErrorText>{error}</S.ErrorText>}
    </S.Wrapper>
  );
}
