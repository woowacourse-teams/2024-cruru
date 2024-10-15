import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  align-items: center;
  gap: 0.4rem;

  & > svg {
    color: ${({ theme }) => theme.baseColors.purplescale[800]};
    width: 1.6rem;
    height: 1.6rem;
  }
`;

const Title = styled.p`
  ${({ theme }) => theme.typography.common.default};
  color: ${({ theme }) => theme.baseColors.grayscale[800]};

  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
`;

const S = {
  Container,
  Title,
};

export default S;
