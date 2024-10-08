import styled from '@emotion/styled';

const EvaluationForm = styled.form`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 1.6rem;
  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};
`;

const FormButtonWrapper = styled.div`
  width: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-end;
  gap: 0.8rem;

  button {
    padding: 0.8rem;
  }
`;

const SpinnerContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  width: 4.5rem;
  height: 1.4rem;
`;

const S = {
  EvaluationForm,
  FormButtonWrapper,
  SpinnerContainer,
};

export default S;
