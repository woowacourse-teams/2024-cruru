import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 2rem;

  width: 100%;
`;

const Title = styled.span`
  ${({ theme }) => theme.typography.heading[400]}
`;

const CloseButton = styled.button``;

const S = {
  Container,
  Title,
  CloseButton,
};

export default S;
