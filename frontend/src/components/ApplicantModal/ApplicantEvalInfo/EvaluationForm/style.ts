import styled from '@emotion/styled';

const Header = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: baseline;
`;

const CancelButton = styled.button`
  color: ${({ theme }) => theme.colors.text.block};
  ${({ theme }) => theme.typography.common.default};
  text-decoration: underline;
`;

const EvaluationForm = styled.form`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 1.2rem;
  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};
`;

const FormButtonWrapper = styled.div`
  width: 100%;
  height: 4rem;

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
`;

const S = {
  Header,
  CancelButton,
  EvaluationForm,
  FormButtonWrapper,
  SpinnerContainer,
};

export default S;
