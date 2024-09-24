import styled from '@emotion/styled';

const FormWrapper = styled.form`
  display: flex;
  flex-direction: column;
  gap: 2.8rem;
  padding: 3rem 2rem;
  box-shadow: 0 0.5rem 2rem rgba(0, 0, 0, 0.1);
  background-color: white;
  width: 100%;
  max-width: 500px;
  margin: auto;
  border-radius: 2.4rem;
`;

const Title = styled.h1`
  ${({ theme }) => theme.typography.heading[700]}
`;

const S = {
  FormWrapper,
  Title,
};

export default S;
