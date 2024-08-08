import styled from '@emotion/styled';
import { css } from '@emotion/react';
import { RecruitmentStatusType } from '@customTypes/recruitment';

const CardWrapper = styled.article`
  width: 38rem;
  height: 20rem;
  border: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
  border-radius: 0.9rem;

  display: flex;
  flex-direction: column;
  transition: all 0.2s ease-in-out;

  &:hover {
    border: 1px solid ${({ theme }) => theme.baseColors.grayscale[700]};
    box-shadow: ${({ theme }) => `0 0.4rem 0.4rem ${theme.baseColors.grayscale[400]}`};
    cursor: pointer;
  }
`;

const RecruitmentInfoContainer = styled.div`
  height: 15rem;
  padding: 1.6rem;

  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;

const RecruitmentTitle = styled.div`
  ${({ theme }) => theme.typography.heading[600]};
  color: ${({ theme }) => theme.colors.text.default};
  height: 4.8rem;
  word-wrap: break-word;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
`;

const RecruitmentStatusFlag = styled.div<{ status: RecruitmentStatusType }>`
  width: fit-content;
  border-radius: 0.4rem;
  padding: 0.5rem 0.8rem;
  ${({ theme }) => theme.typography.common.small};

  ${({ theme, status }) => {
    if (status === 'planned') {
      return css`
        border: 1px solid ${theme.baseColors.bluescale[100]};
        background: ${theme.baseColors.bluescale[50]};
        color: ${theme.baseColors.bluescale[500]};
      `;
    }
    if (status === 'inProgress') {
      return css`
        border: 1px solid ${theme.baseColors.purplescale[100]};
        background: ${theme.baseColors.purplescale[50]};
        color: ${theme.baseColors.purplescale[500]};
      `;
    }
    return css`
      border: 1px solid ${theme.baseColors.grayscale[300]};
      background: ${theme.baseColors.grayscale[200]};
      color: ${theme.baseColors.grayscale[700]};
    `;
  }}
`;

const EndDateContainer = styled.div`
  color: ${({ theme }) => theme.baseColors.grayscale[800]};
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 0.5rem;

  span {
    ${({ theme }) => theme.typography.heading[400]};
    color: ${({ theme }) => theme.baseColors.grayscale[700]};
  }
`;

const RecruitmentResultContainer = styled.div`
  border-top: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
  padding: 1.6rem;
  position: relative;

  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;

  button {
    position: absolute;
    right: 1.6rem;
  }
`;

const PostStatsContainer = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;

  span.statNumbers {
    color: ${({ theme }) => theme.colors.text.default};
    margin-left: 0.5rem;
  }

  div.totalStats {
    padding-right: 1.6rem;
    border-right: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
  }

  div.stats {
    padding-left: 1.6rem;
  }
`;

const S = {
  CardWrapper,
  RecruitmentInfoContainer,
  RecruitmentTitle,
  RecruitmentStatusFlag,
  EndDateContainer,
  RecruitmentResultContainer,
  PostStatsContainer,
};

export default S;
