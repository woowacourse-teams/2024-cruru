import styled from '@emotion/styled';
import { css } from '@emotion/react';

const StatContainer = styled.div<{ isTotalStats: boolean }>`
  ${({ theme }) => theme.typography.heading[400]};
  color: ${({ theme }) => theme.baseColors.grayscale[700]};

  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 0.5rem;

  ${({ isTotalStats, theme }) => {
    if (isTotalStats) {
      return css`
        padding-right: 1.6rem;
        border-right: 1px solid ${theme.baseColors.grayscale[400]};
      `;
    }
    return css`
      padding-left: 1.6rem;
    `;
  }}
`;

const StatNumber = styled.span`
  color: ${({ theme }) => theme.baseColors.grayscale[900]};
`;

const S = {
  StatContainer,
  StatNumber,
};

export default S;
