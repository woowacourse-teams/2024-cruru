import { ComponentProps } from 'react';
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
