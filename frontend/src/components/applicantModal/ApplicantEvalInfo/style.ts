import styled from '@emotion/styled';

const Wrapper = styled.section`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
`;

const FormContainer = styled.div`
  padding: 2.4rem 1.6rem;
  border-top: 1px solid ${({ theme }) => theme.baseColors.grayscale[500]};
  border-bottom: 1px solid ${({ theme }) => theme.baseColors.grayscale[500]};
`;

const EvaluationListContainer = styled.ul`
  & > li {
    padding: 1.4rem 0;
    border-bottom: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
  }
`;

const S = {
  Wrapper,
  FormContainer,
  EvaluationListContainer,
};

export default S;
