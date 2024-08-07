import styled from '@emotion/styled';
import { css } from '@emotion/react';

const CardContainer = styled.li`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 1.2rem;
  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};
`;

const EvaluatorDetailContainer = styled.div`
  display: flex;
  flex-direction: row;
  gap: 1.2rem;
`;

const EvaluatorImagePlaceholder = styled.div`
  width: 3.6rem;
  height: 3.6rem;
  border-radius: 0.8rem;
  background-color: ${({ theme }) => theme.baseColors.grayscale[400]};
`;

const EvaluatorDetail = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.2rem;
`;

const EvaluatorName = styled.h3`
  ${({ theme }) => theme.typography.common.default};
  color: black;
`;

const EvaluatedDate = styled.span`
  ${({ theme }) => theme.typography.common.small};
  color: ${({ theme }) => theme.colors.text.block};
`;

const ResultFlag = styled.div<{ $score: string }>`
  ${({ theme }) => theme.typography.common.small};
  width: fit-content;
  display: inline-block;
  border-radius: 0.4rem;
  padding: 0.4rem 0.6rem;

  ${({ theme, $score }) => {
    const score = Number($score);
    switch (score) {
      case 1:
        return css`
          color: ${theme.baseColors.redscale[500]};
          background-color: ${theme.baseColors.redscale[50]};
        `;
      case 2:
        return css`
          color: ${theme.baseColors.redscale[400]};
          background-color: ${theme.baseColors.redscale[50]};
        `;
      case 3:
        return css`
          color: ${theme.baseColors.grayscale[700]};
          background-color: ${theme.baseColors.grayscale[300]};
        `;
      case 4:
        return css`
          color: ${theme.baseColors.bluescale[400]};
          background-color: ${theme.baseColors.bluescale[50]};
        `;
      case 5:
        return css`
          color: ${theme.baseColors.bluescale[500]};
          background-color: ${theme.baseColors.bluescale[50]};
        `;
      default:
        return css`
          color: ${theme.baseColors.grayscale[700]};
          background-color: ${theme.baseColors.grayscale[100]};
        `;
    }
  }}
`;

const ResultComment = styled.div`
  ${({ theme }) => theme.typography.common.small};
  color: ${({ theme }) => theme.colors.text.block};
`;

const S = {
  CardContainer,
  EvaluatorDetailContainer,
  EvaluatorImagePlaceholder,
  EvaluatorDetail,
  EvaluatorName,
  EvaluatedDate,
  ResultFlag,
  ResultComment,
};

export default S;
