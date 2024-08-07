import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  min-width: 50rem;
  gap: 4rem;
`;

const DateContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.6rem;
`;

const Title = styled.h2`
  ${({ theme }) => theme.typography.heading[500]}
`;

const Description = styled.p`
  margin-top: -1.2rem;
  ${({ theme }) => theme.typography.common.default}
  color: ${({ theme }) => theme.baseColors.grayscale[800]};
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

const NextButtonContainer = styled.div`
  display: flex;
  justify-content: end;
`;

const ButtonContent = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;

  padding: 0.2rem 0.4rem;
  gap: 0.4rem;
`;

const S = {
  Container,
  DateContainer,
  DatePickerBox,
  Title,
  Description,
  DatePickerContainer,
  RecruitTitleContainer,
  RecruitDetailContainer,
  NextButtonContainer,
  ButtonContent,
};

export default S;
