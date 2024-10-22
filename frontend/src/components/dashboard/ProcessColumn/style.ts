import styled from '@emotion/styled';
import { hideScrollBar } from '@styles/utils';

const ProcessWrapper = styled.section<{ isPassedColumn: boolean }>`
  width: 28rem;
  min-width: 28rem;
  height: 100%;

  padding: 1.2rem;
  border-radius: 0.8rem;
  background-color: ${({ theme, isPassedColumn }) => (isPassedColumn ? '#EEF5E4' : theme.baseColors.grayscale[100])};
`;

const Header = styled.header`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: space-between;
  gap: 0.8rem;

  padding: 0.8rem 0.4rem;
  margin-bottom: 2rem;
  border-bottom: 0.1rem solid ${({ theme }) => theme.baseColors.grayscale[400]};
`;

const TitleContainer = styled.div`
  display: flex;
  flex-direction: row;
  align-items: baseline;
  justify-content: space-between;
  gap: 1.6rem;
  width: 100%;
`;

const Title = styled.h2`
  ${({ theme }) => theme.typography.heading[500]};
  padding: 0.4rem;
`;

const CheckboxContainer = styled.div`
  margin-top: 0.4rem;
`;

const ApplicantList = styled.ul`
  width: 100%;
  height: 100%;
  max-height: 85%;

  display: flex;
  flex-direction: column;
  padding: 0.4rem;

  gap: 1.2rem;

  overflow-y: scroll;
  overflow-x: visible;
  ${hideScrollBar};
`;

const S = {
  ProcessWrapper,
  Header,
  Title,
  TitleContainer,
  CheckboxContainer,
  ApplicantList,
};

export default S;
