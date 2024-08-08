import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 4rem;
`;

const Icon = styled.div`
  font-size: 8rem;
`;

const Message = styled.h2`
  ${({ theme }) => theme.typography.heading[700]}
`;

const ButtonContent = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0.5rem;
  padding: 0 0.4rem;

  ${({ theme }) => theme.typography.common.default}
`;

const S = {
  Container,
  Icon,
  Message,
  ButtonContent,
};

export default S;
