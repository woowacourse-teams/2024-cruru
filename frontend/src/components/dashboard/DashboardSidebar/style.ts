import styled from '@emotion/styled';

const Container = styled.div`
  width: 300px;
  border-right: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
  padding: 3.6rem 1.6rem;

  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};
  border-radius: 1.6rem 0 0 1.6rem;
`;

const Logo = styled.div`
  ${({ theme }) => theme.typography.heading[700]}
  margin-bottom: 3.2rem;
`;

const Contents = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.4rem;
`;

const NavButton = styled.button<{ isSelected: boolean }>`
  ${({ theme }) => theme.typography.common.block}
  color: ${({ theme, isSelected }) => (isSelected ? theme.colors.brand.primary : theme.colors.text.default)};
  margin-bottom: 0;

  &::before {
    content: '•';
    width: 1rem;
    aspect-ratio: 1/1;
    margin: 0 0.8rem;
  }
`;

const Link = styled.nav`
  cursor: pointer;
`;

const S = {
  Container,
  Logo,
  Contents,
  NavButton,
  Link,
};

export default S;
