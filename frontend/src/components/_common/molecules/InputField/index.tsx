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
  const descriptionId = description ? `${id}-description` : undefined;
  const errorId = error ? `${id}-error` : undefined;

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
          {required && <S.Asterisk />}
          {required && <HiddenElementForSR>필수 질문입니다.</HiddenElementForSR>}
        </S.LabelWrapper>
      )}

      {description && (
        <S.Description
          id={descriptionId}
          disabled={!!disabled}
        >
          {description}
        </S.Description>
      )}
      <S.Input
        id={id}
        value={value}
        onChange={onChange}
        disabled={disabled}
        aria-describedby={`${descriptionId || ''} ${errorId || ''}`}
        required={required}
        isError={!!error}
        {...props}
      />

      {(isLengthVisible || error) && (
        <S.Footer isError={!!error}>
          {error && (
            <S.ErrorText
              id={errorId}
              role="alert"
            >
              {error}
            </S.ErrorText>
          )}
          {isLengthVisible && (
            <S.LengthText aria-live="polite">
              {`${value ? value.toString().length : 0} / ${props.maxLength}`}
            </S.LengthText>
          )}
        </S.Footer>
      )}
    </S.Wrapper>
  );
}
