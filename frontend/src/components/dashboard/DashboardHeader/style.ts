import styled from '@emotion/styled';

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
  margin-bottom: 1.2rem;
`;

const Title = styled.div`
  ${({ theme }) => theme.typography.heading[700]}
`;

const RecruitmentStatusContainer = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 1.6rem;
`;

const RecruitmentPeriod = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 0.6rem;

  ${({ theme }) => theme.typography.heading[400]};
  color: ${({ theme }) => theme.baseColors.grayscale[600]};
`;

const S = {
  Wrapper,
  Title,
  RecruitmentStatusContainer,
  RecruitmentPeriod,
};

export default S;
