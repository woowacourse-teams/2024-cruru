import styled from '@emotion/styled';

const Wrapper = styled.div`
  width: 100%;
  display: flex;
  flex-direction: row;
  align-items: flex-start;
`;

const QuestionBuilderContainer = styled.div`
  width: calc(100% - 3.6rem);
  display: flex;
  flex-direction: column;
  gap: 1.6rem;

  padding: 2.4rem;
  border: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
  border-radius: 0.8rem 0 0.8rem 0.8rem;
`;

const InputBox = styled.div`
  width: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 2.8rem;
`;

const RequiredBox = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  align-items: center;
  gap: 0.8rem;

  ${({ theme }) => theme.typography.common.small};
  color: ${({ theme }) => theme.baseColors.grayscale[600]};
`;

const ControlButtonContainer = styled.div`
  width: 3.6rem;
  display: flex;
  flex-direction: column;
`;

const S = {
  Wrapper,
  QuestionBuilderContainer,
  InputBox,
  RequiredBox,
  ControlButtonContainer,
};

export default S;
