import styled from '@emotion/styled';

const Wrapper = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  padding-bottom: 1.2rem;
`;

const TitleContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
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

const PostLinkContainer = styled.div`
  display: flex;
  gap: 0.8rem;
  min-width: 30rem;
  height: fit-content;
`;

const S = {
  Wrapper,
  TitleContainer,
  Title,
  RecruitmentStatusContainer,
  RecruitmentPeriod,
  PostLinkContainer,
};

export default S;
