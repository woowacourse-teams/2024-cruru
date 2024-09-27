import styled from '@emotion/styled';

const Container = styled.div`
  height: 9rem;
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 1rem;

  ${({ theme }) => theme.typography.heading[600]}
`;

const Title = styled.div`
  display: flex;
  align-items: center;
`;

const Description = styled.div`
  ${({ theme }) => theme.typography.common.paragraph}
`;

const S = {
  Container,
  Title,
  Description,
};

export default S;
