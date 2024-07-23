import { ComponentProps } from 'react';
import S from './style';

interface TextFieldProps extends ComponentProps<'textarea'> {
  label?: string;
  error?: string;
  focus?: boolean;
  resize?: boolean;
}

export default function TextField({
  label,
  value,
  onChange,
  disabled,
  error,
  required,
  resize = true,
  ...props
}: TextFieldProps) {
  return (
    <S.Wrapper>
      {label && (
        <S.LabelWrapper>
          <S.Label disabled={!!disabled}>{label}</S.Label>
          {required && <S.Asterisk />}
        </S.LabelWrapper>
      )}

      <S.TextArea
        value={value}
        onChange={onChange}
        disabled={disabled}
        isError={!!error}
        resize={resize}
        {...props}
      />

      {error && <S.ErrorText>{error}</S.ErrorText>}
    </S.Wrapper>
  );
}
