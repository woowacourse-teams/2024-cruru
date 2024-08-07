import styled from '@emotion/styled';

const Wrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  min-width: 20rem;
  height: 4.2rem;
  padding: 0.8rem 1.2rem;

  border: 0.1rem solid ${({ theme }) => theme.baseColors.grayscale[400]};
  border-radius: 1rem;

  svg {
    color: ${({ theme }) => theme.baseColors.purplescale[500]};
  }
`;

const LinkContainer = styled.div`
  display: flex;
  align-items: center;

  width: 100%;
`;

const Link = styled.a`
  ${({ theme }) => theme.typography.common.large};
  color: ${({ theme }) => theme.colors.link};
`;

const S = {
  Wrapper,
  LinkContainer,
  Link,
};

export default S;
