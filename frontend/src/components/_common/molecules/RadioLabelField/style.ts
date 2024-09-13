import styled from '@emotion/styled';

const Wrapper = styled.fieldset`
  display: flex;
  flex-direction: column;
  gap: 1.6rem;

  width: 100%;
`;

const HeadWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
`;

const LabelWrapper = styled.legend`
  display: flex;
  align-items: center;
  gap: 0.4rem;
`;

const DescriptionWrapper = styled.div`
  ${({ theme }) => theme.typography.common.default};
  color: ${({ theme }) => theme.baseColors.grayscale[800]};
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

const ErrorText = styled.p`
  color: ${({ theme }) => theme.colors.feedback.error};
  ${({ theme }) => theme.typography.common.default};
`;

const OptionsWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
`;

const Option = styled.div`
  display: flex;
  align-items: center;
  gap: 0.8rem;
`;

const OptionLabel = styled.div`
  ${({ theme }) => theme.typography.common.default}
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  width: 90%;
`;

const S = {
  LabelWrapper,
  DescriptionWrapper,
  Label,
  HeadWrapper,
  Asterisk,
  Wrapper,
  OptionsWrapper,
  Option,
  OptionLabel,
  ErrorText,
};

export default S;
