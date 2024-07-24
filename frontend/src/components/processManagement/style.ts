import styled from '@emotion/styled';

const ProcessForm = styled.form`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 2.4rem;

  padding: 2.4rem 1.6rem;

  border-radius: 0.8rem;
  border: 0.1rem solid ${({ theme }) => theme.baseColors.grayscale[300]};
  background-color: ${({ theme }) => theme.baseColors.grayscale[100]};
`;

const ButtonWrapper = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.8rem;

  button {
    padding: 0.8rem;
  }
`;

const C = {
  ProcessForm,
  ButtonWrapper,
};

export default C;
