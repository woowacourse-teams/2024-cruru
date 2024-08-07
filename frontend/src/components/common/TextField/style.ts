import { Theme, css } from '@emotion/react';
import styled from '@emotion/styled';

// TODO: InputFieldd와 중복되는 부분이 다수 있어 공통으로 분리
const commonInputStyles = (theme: Theme) => css`
  ${theme.typography.common.default};

  border-radius: 0.8rem;
  padding: 1.2rem 1.6rem;
  outline: none;
  background-color: ${theme.baseColors.grayscale[50]};
  border: 0.1rem solid ${theme.colors.text.block};
  color: ${theme.colors.text.default};
`;

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;

  width: 100%;
`;

const LabelWrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 0.4rem;
`;

const Label = styled.label<{ disabled: boolean }>`
  ${({ theme }) => theme.typography.heading[500]};
  color: ${({ theme, disabled }) => (disabled ? theme.baseColors.grayscale[500] : theme.colors.text.default)};
`;

const Asterisk = styled.span`
  display: block;

  &::before {
    content: '*';
  }
  color: ${({ theme }) => theme.colors.feedback.error};
  font-size: ${({ theme }) => theme.typography.heading[500]};
`;

const TextArea = styled.textarea<{ isError: boolean; resize: boolean }>`
  ${({ theme }) => commonInputStyles(theme)}
  ${({ theme, isError }) => css`
    &::placeholder {
      ${theme.typography.common.block};
      font-weight: 600;
      color: ${theme.baseColors.grayscale[600]};
    }

    &:focus {
      color: ${theme.colors.text.default};
      border-color: ${theme.baseColors.purplescale[500]};
    }

    &:disabled {
      color: ${theme.baseColors.grayscale[500]};
      background-color: ${theme.baseColors.grayscale[200]};
      border-color: ${theme.baseColors.grayscale[500]};
    }

    ${isError && `border-color: ${theme.colors.feedback.error};`}
  `}
  ${({ resize }) => !resize && 'resize: none;'}
`;

const ErrorText = styled.p`
  color: ${({ theme }) => theme.colors.feedback.error};
  ${({ theme }) => theme.typography.common.default};
`;

const S = {
  LabelWrapper,
  Label,
  Asterisk,
  Wrapper,
  TextArea,
  ErrorText,
};

export default S;
