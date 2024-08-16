import styled from '@emotion/styled';

const Container = styled.div`
  width: 100%;

  display: flex;
  align-items: center;
  gap: 0.8rem;

  padding: 0.8rem;

  border-bottom: 1px solid ${({ theme }) => theme.baseColors.grayscale[500]};

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
