import styled from '@emotion/styled';
import { css } from '@emotion/react';
import { RecruitmentStatusType } from '@customTypes/recruitment';

const FlagContainer = styled.div<{ status: RecruitmentStatusType }>`
  width: fit-content;
  border-radius: 0.4rem;
  padding: 0.5rem 0.8rem;
  ${({ theme }) => theme.typography.common.small};

  ${({ theme, status }) => {
    if (status === 'Pending') {
      return css`
        border: 1px solid ${theme.baseColors.bluescale[100]};
        background: ${theme.baseColors.bluescale[50]};
        color: ${theme.baseColors.bluescale[500]};
      `;
    }
    if (status === 'Ongoing') {
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

const S = {
  FlagContainer,
};

export default S;
