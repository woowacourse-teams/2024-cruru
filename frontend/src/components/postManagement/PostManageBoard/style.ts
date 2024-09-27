import styled from '@emotion/styled';

const Wrapper = styled.div`
  width: 100%;
  height: 100%;
  padding: 2rem 6rem;

  display: flex;
  flex-direction: column;
  gap: 4rem;

  overflow-y: auto;
`;

const Section = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.6rem;
`;

const SectionTitleContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
  ${({ theme }) => theme.typography.common.default};

  h2 {
    ${({ theme }) => theme.typography.heading[500]};
  }

  span {
    color: ${({ theme }) => theme.baseColors.grayscale[800]};
  }
`;

const DatePickerContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;

  max-width: 50rem;
`;

const DatePickerBox = styled.div`
  width: 22rem;
`;

const RecruitTitleContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
`;

const RecruitDetailContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
  height: 44rem;
  margin-bottom: 5.2rem;
`;

const ModifyButtonContainer = styled.div`
  width: 100%;
  max-width: 30rem;
  height: 5.2rem;
  margin: 2.4rem auto;
`;

const S = {
  Wrapper,
  Section,
  SectionTitleContainer,

  DatePickerContainer,
  DatePickerBox,

  RecruitTitleContainer,
  RecruitDetailContainer,

  ModifyButtonContainer,
};

export default S;
