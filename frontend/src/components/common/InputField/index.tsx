import { ComponentProps } from 'react';
import S from './style';

interface InputFieldProps extends ComponentProps<'input'> {
  label?: string;
  error?: string;
  focus?: boolean;
  isLengthVisible?: boolean;
}

export default function InputField({
  label,
  value,
  onChange,
  disabled,
  error,
  required,
  isLengthVisible,
  ...props
}: InputFieldProps) {
  return (
    <S.Wrapper>
      {label && (
        <S.LabelWrapper>
          <S.Label disabled={!!disabled}>{label}</S.Label>
          {required && <S.Asterisk />}
        </S.LabelWrapper>
      )}

      <S.Input
        value={value}
        onChange={onChange}
        disabled={disabled}
        isError={!!error}
        required={required}
        {...props}
      />

      {(isLengthVisible || error) && (
        <S.Footer isError={!!error}>
          {error && <S.ErrorText>{error}</S.ErrorText>}
          {isLengthVisible && (
            <S.LengthText>{`${value ? value.toString().length : 0} / ${props.maxLength}`}</S.LengthText>
          )}
        </S.Footer>
      )}
    </S.Wrapper>
  );
}
