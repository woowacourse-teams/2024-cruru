import styled from '@emotion/styled';

const Wrapper = styled.section`
  width: 100%;
  height: 100%;

  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;

const FormContainer = styled.div`
  padding-top: 1.6rem;
  border-top: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
`;

const EvaluationListContainer = styled.ul`
  & > li {
    padding: 1.4rem 0;
    border-bottom: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
  }

  height: 100%;
  overflow-y: auto;
`;

const S = {
  Wrapper,
  FormContainer,
  EvaluationListContainer,
};

export default S;
