import styled from '@emotion/styled';

const CardContainer = styled.article`
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

const ResultFlag = styled.div`
  ${({ theme }) => theme.typography.common.small};
  width: fit-content;
  display: inline-block;
  border-radius: 0.4rem;
  padding: 0.4rem 0.6rem;
  background-color: ${({ theme }) => theme.baseColors.grayscale[400]};
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
