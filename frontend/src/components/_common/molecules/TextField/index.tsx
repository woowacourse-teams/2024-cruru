import { ComponentProps, useId } from 'react';
import HiddenElementForSR from '@components/_common/atoms/ScreenReaderHidden';
import S from './style';

interface TextFieldProps extends ComponentProps<'textarea'> {
  label?: string;
  error?: string;
  focus?: boolean;
  resize?: boolean;
  isLengthVisible?: boolean;
}

export default function TextField({
  label,
  value,
  onChange,
  disabled,
  error,
  required,
  resize = true,
  isLengthVisible,
  ...props
}: TextFieldProps) {
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
          {required && <HiddenElementForSR>필수 입력 요소입니다.</HiddenElementForSR>}
        </S.LabelWrapper>
      )}

      <S.TextArea
        id={id}
        value={value}
        onChange={onChange}
        disabled={disabled}
        isError={!!error}
        resize={resize}
        required={required}
        {...props}
      />

      {(isLengthVisible || error) && (
        <S.Footer isError={!!error}>
          {error && <S.ErrorText role="alert">{error}</S.ErrorText>}
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
