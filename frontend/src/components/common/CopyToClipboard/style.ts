import styled from '@emotion/styled';

const Wrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  min-width: 20rem;
  height: fit-content;
  max-height: 4rem;
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

  width: 90%;
`;

const Link = styled.span`
  a {
    display: flex;
    align-items: center;
    gap: 0.4rem;

    ${({ theme }) => theme.typography.common.default};
    color: ${({ theme }) => theme.baseColors.grayscale[800]};

    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;

    svg {
      display: block;
      width: 1.6rem;
      height: 1.6rem;
    }
  }
`;

const S = {
  Wrapper,
  LinkContainer,
  Link,
};

export default S;
