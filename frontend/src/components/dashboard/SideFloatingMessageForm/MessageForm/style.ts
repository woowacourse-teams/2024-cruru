import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;

  max-width: 500px;
  padding: 2rem 2rem;
  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};
  box-shadow: 0 0.5rem 2rem rgba(0, 0, 0, 0.1);

  border-radius: 0.8rem;
`;

const FormWrapper = styled.form`
  display: flex;
  flex-direction: column;
  gap: 2rem;

  width: 100%;
`;

const Title = styled.h1`
  ${({ theme }) => theme.typography.heading[500]}
`;

const FormHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const ControlIcons = styled.div`
  display: flex;
  gap: 1rem;
`;

const IconButton = styled.button`
  padding: 0.4rem;
  border-radius: 100%;
  &:hover {
    background-color: ${({ theme }) => theme.baseColors.grayscale[100]};
  }
`;

const S = {
  Container,
  FormWrapper,
  Title,
  FormHeader,
  ControlIcons,
  IconButton,
};

export default S;
