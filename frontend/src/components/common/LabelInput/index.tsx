import { ComponentProps } from 'react';
import S from './style';

interface InputProps extends ComponentProps<'input'> {
  label?: string;
  error?: boolean;
  focus?: boolean;
}

export default function LabelInput({
  label,
  value,
  onChange,
  placeholder,
  disabled,
  error,
  type,
  required,
  ...props
}: InputProps) {
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
        placeholder={placeholder}
        disabled={disabled}
        type={type}
        isError={!!error}
        {...props}
      />

      {error && <S.ErrorText>{error}</S.ErrorText>}
    </S.Wrapper>
  );
}
