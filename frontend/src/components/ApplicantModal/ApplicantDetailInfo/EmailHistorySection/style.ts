import styled from '@emotion/styled';

const Container = styled.div`
  width: 100%;
  padding: 1.6rem;

  display: flex;
  flex-direction: column;
  gap: 1.6rem;
`;

const EmailFormContainer = styled.div`
  width: 100%;
  padding: 1.6rem 0.8rem;
  border-top: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
  border-bottom: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
`;

const EmailButtonContainer = styled.div`
  width: 6.4rem;
  height: 3.2rem;
`;

const Header = styled.div`
  padding: 0 0.8rem;

  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const Title = styled.div`
  ${({ theme }) => theme.typography.heading[500]};
  color: ${({ theme }) => theme.baseColors.grayscale[700]};
`;

const ContentContainer = styled.div`
  width: 100%;

  display: flex;
  flex-direction: column;

  border: 1px solid ${({ theme }) => theme.baseColors.grayscale[300]};
`;

const S = {
  Container,
  EmailFormContainer,
  EmailButtonContainer,
  Header,
  Title,
  ContentContainer,
};

export default S;
