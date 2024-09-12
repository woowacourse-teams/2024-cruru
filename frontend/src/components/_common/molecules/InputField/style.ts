import { Theme, css } from '@emotion/react';
import styled from '@emotion/styled';

const commonInputStyles = (theme: Theme) => css`
  ${theme.typography.common.default};

  border-radius: 0.8rem;
  padding: 1.2rem 1.6rem;
  outline: none;
  background-color: ${theme.baseColors.grayscale[50]};
  border: 0.1rem solid ${theme.baseColors.grayscale[400]};
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

const Description = styled.p<{ disabled: boolean }>`
  ${({ theme }) => theme.typography.common.small};
  color: ${({ theme, disabled }) => (disabled ? theme.baseColors.grayscale[400] : theme.baseColors.grayscale[600])};
`;

const Input = styled.input<{ isError: boolean }>`
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
`;

const Footer = styled.div<{ isError: boolean }>`
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  justify-content: ${({ isError }) => (isError ? 'space-between' : 'flex-end')};
  gap: 1rem;
`;

const ErrorText = styled.p`
  color: ${({ theme }) => theme.colors.feedback.error};
  ${({ theme }) => theme.typography.common.small};
`;

const LengthText = styled.p`
  min-width: fit-content;
  ${({ theme }) => theme.typography.common.small};
  color: ${({ theme }) => theme.baseColors.grayscale[600]};
`;

const S = {
  LabelWrapper,
  Label,
  Description,
  Asterisk,
  Wrapper,
  Input,
  Footer,
  ErrorText,
  LengthText,
};

export default S;
