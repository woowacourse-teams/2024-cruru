import { ComponentProps } from 'react';
import S from './style';

interface InputProps extends ComponentProps<'input'> {
  label?: string;
  error?: boolean;
  focus?: boolean;
}

export default function LabelInput({ label, value, onChange, disabled, error, required, ...props }: InputProps) {
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
        {...props}
      />

      {error && <S.ErrorText>{error}</S.ErrorText>}
    </S.Wrapper>
  );
}
