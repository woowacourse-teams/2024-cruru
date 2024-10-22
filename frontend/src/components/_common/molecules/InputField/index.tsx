import { ComponentProps, useId } from 'react';
import HiddenElementForSR from '@components/_common/atoms/ScreenReaderHidden';
import S from './style';

interface InputFieldProps extends ComponentProps<'input'> {
  label?: string;
  description?: string;
  error?: string;
  focus?: boolean;
  isLengthVisible?: boolean;
}

export default function InputField({
  label,
  description,
  value,
  onChange,
  disabled,
  error,
  required,
  isLengthVisible,
  ...props
}: InputFieldProps) {
  const id = useId();

  return (
    <S.Wrapper>
      {label && (
        <S.LabelWrapper>
          <S.Label
            htmlFor={id}
            disabled={!!disabled}
          >
            {label}
          </S.Label>
          {required && <S.Asterisk aria-hidden />}
          {required && <HiddenElementForSR>필수 질문입니다.</HiddenElementForSR>}
        </S.LabelWrapper>
      )}

      {description && <S.Description disabled={!!disabled}>{description}</S.Description>}
      <S.Input
        id={id}
        value={value}
        onChange={onChange}
        disabled={disabled}
        required={required}
        isError={!!error}
        {...props}
      />

      {(isLengthVisible || error) && (
        <S.Footer isError={!!error}>
          {error && <S.ErrorText role="alert">{error}</S.ErrorText>}
          {isLengthVisible && (
            <S.LengthText
              aria-live="polite"
              aria-label="현재 글자 수"
            >
              {`${value ? value.toString().length : 0} / ${props.maxLength}`}
            </S.LengthText>
          )}
        </S.Footer>
      )}
    </S.Wrapper>
  );
}
