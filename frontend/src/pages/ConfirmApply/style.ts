import styled from '@emotion/styled';

const PageLayout = styled.div`
  width: 100vw;
  height: 100vh;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2.4rem;

  svg {
    color: ${({ theme }) => theme.colors.brand.primary};
    width: 6.4rem;
    height: 6.4rem;
  }
`;

const PostTitleContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.8rem;
`;

const Title = styled.h1`
  ${({ theme }) => theme.typography.heading[600]};
`;

const Information = styled.p`
  ${({ theme }) => theme.typography.common.large};
  color: ${({ theme }) => theme.baseColors.grayscale[700]};
  line-height: 140%;
  text-align: center;
`;

const ButtonContainer = styled.div`
  width: 100%;
  max-width: 15rem;
  height: 4rem;
`;

const S = {
  PageLayout,
  Wrapper,
  PostTitleContainer,
  Title,
  Information,
  ButtonContainer,
};

export default S;
