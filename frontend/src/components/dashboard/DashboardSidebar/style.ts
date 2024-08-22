import styled from '@emotion/styled';

const Container = styled.div`
  position: relative;

  width: 20%;
  min-width: 25rem;
  max-width: 30rem;
  border-right: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
  padding: 3.6rem 1.6rem;

  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};
  border-radius: 1.6rem 0 0 1.6rem;

  display: flex;
  flex-direction: column;
  gap: 3.2rem;
`;

const Logo = styled.img`
  height: 2.4rem;
`;

const Contents = styled.nav`
  display: flex;
  flex-direction: column;
  gap: 1.4rem;
`;

const LinkContainer = styled.div<{ isSelected: boolean }>`
  ${({ theme }) => theme.typography.common.block}
  color: ${({ theme, isSelected }) => (isSelected ? theme.colors.brand.primary : theme.colors.text.default)};
  margin-bottom: 0;

  display: flex;
  align-items: flex-start;

  & > button > a {
    text-align: start;
  }

  &::before {
    content: '•';
    display: block;
    width: 1rem;
    aspect-ratio: 1/1;
    margin: 0 0.8rem;
  }
`;

const S = {
  Container,
  Logo,
  Contents,
  LinkContainer,
};

export default S;
