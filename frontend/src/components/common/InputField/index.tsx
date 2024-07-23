import { ComponentProps } from 'react';
import S from './style';

interface InputFieldProps extends ComponentProps<'input'> {
  label?: string;
  error?: string;
  focus?: boolean;
}

export default function InputField({ label, value, onChange, disabled, error, required, ...props }: InputFieldProps) {
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

      {error && <S.ErrorText>{error}</S.ErrorText>}
    </S.Wrapper>
  );
}
