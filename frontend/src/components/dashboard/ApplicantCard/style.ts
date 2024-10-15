import styled from '@emotion/styled';
import { css } from '@emotion/react';

const CardContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  min-width: 15rem;
  padding: 1rem 1.6rem;
  border-radius: 0.8rem;
  user-select: none;

  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};
  border: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};

  transition: all 0.2s;

  &:hover {
    scale: 1.01;
    transform: translateY(-0.1rem);
    box-shadow: 0 0.2rem 0.6rem rgba(0, 0, 0, 0.1);
    border: 1px solid ${({ theme }) => theme.baseColors.grayscale[500]};
    cursor: pointer;
    z-index: 9;
  }
`;

const CardDetail = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
`;

const CardHeader = styled.h3`
  ${({ theme }) => theme.typography.common.block};
  color: ${({ theme }) => theme.colors.text.default};
  margin-bottom: 0;
`;

const CardEvaluationFlag = styled.div<{ averageScore: number; isScoreExists: boolean }>`
  width: 4rem;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  border-radius: 0.4rem;
  padding: 0.3rem 0.4rem;
  margin-left: -0.1rem;

  ${({ theme }) => theme.colors.text.block};

  ${({ theme, averageScore, isScoreExists }) => {
    if (!isScoreExists) {
      return css`
        background: ${theme.baseColors.grayscale[300]};
        color: ${theme.colors.text.block};
      `;
    }

    if (averageScore >= 1 && averageScore < 2) {
      return css`
        color: ${theme.baseColors.redscale[500]};
        background: ${theme.baseColors.redscale[50]};
      `;
    }

    if (averageScore >= 2 && averageScore < 3) {
      return css`
        color: ${theme.baseColors.redscale[400]};
        background: ${theme.baseColors.redscale[50]};
      `;
    }

    if (averageScore >= 3 && averageScore < 4) {
      return css`
        color: ${theme.baseColors.bluescale[400]};
        background: ${theme.baseColors.bluescale[50]};
      `;
    }

    if (averageScore >= 4 && averageScore <= 5) {
      return css`
        color: ${theme.baseColors.bluescale[500]};
        background: ${theme.baseColors.bluescale[50]};
      `;
    }

    return css`
      background: ${theme.baseColors.grayscale[300]};
    `;
  }}
`;

const CardEvaluationFlagScore = styled.span<{ isScoreExists: boolean }>`
  padding-right: ${({ isScoreExists }) => (isScoreExists ? '0' : '0.2rem')};
`;

const CardInfoContainer = styled.div`
  display: flex;
  flex-direction: row;
  gap: 1.6rem;

  ${({ theme }) => theme.typography.common.small};
  color: ${({ theme }) => theme.baseColors.grayscale[800]};
`;

const CardInfo = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-start;
  gap: 0.6rem;
`;

const OptionButtonWrapper = styled.div`
  position: relative;
`;

const S = {
  CardContainer,
  CardDetail,
  CardHeader,
  CardEvaluationFlag,
  CardEvaluationFlagScore,
  CardInfoContainer,
  CardInfo,
  OptionButtonWrapper,
};

export default S;
