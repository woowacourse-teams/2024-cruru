import { css } from '@emotion/react';
import styled from '@emotion/styled';

const Container = styled.div<{ showMore: boolean }>`
  width: 100%;
  display: flex;
  align-items: baseline;

  ${({ showMore }) =>
    showMore &&
    css`
      flex-direction: column;
      align-items: flex-end;
    `}
`;

const Description = styled.p<{ showMore: boolean }>`
  width: 100%;

  ${({ theme }) => theme.typography.common.block};
  color: ${({ theme }) => theme.baseColors.grayscale[700]};
  margin-bottom: 0;
  padding: 0.4rem;

  ${({ showMore }) =>
    !showMore &&
    css`
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    `}
`;

const MoreButton = styled.button`
  ${({ theme }) => theme.typography.common.block};
  color: ${({ theme }) => theme.baseColors.grayscale[500]};

  white-space: nowrap;
  margin: 0;
`;

const S = {
  Container,
  Description,
  MoreButton,
};

export default S;
