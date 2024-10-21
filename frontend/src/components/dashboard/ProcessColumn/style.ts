import styled from '@emotion/styled';
import { hideScrollBar } from '@styles/utils';

const ProcessWrapper = styled.section<{ isPassedColumn: boolean }>`
  width: 28rem;
  min-width: 28rem;
  overflow-y: hidden;

  padding: 1.2rem;
  border-radius: 0.8rem;
  border: 0.1rem solid ${({ theme }) => theme.baseColors.grayscale[400]};
  background-color: ${({ theme, isPassedColumn = false }) =>
    isPassedColumn ? '#F9FFF9' : theme.baseColors.grayscale[50]};

  display: flex;
  flex-direction: column;
  gap: 2rem;
`;

const Header = styled.header`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: space-between;
  gap: 0.8rem;

  padding: 0.8rem 0.4rem;
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

const TitleBox = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-start;
  gap: 0.8rem;
`;

const Title = styled.h2`
  ${({ theme }) => theme.typography.heading[500]};
  padding: 0.4rem;
`;

const ApplicantNumber = styled.div`
  ${({ theme }) => theme.typography.heading[100]};
  color: ${({ theme }) => theme.colors.text.block};
  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};

  padding: 0.2rem 0.8rem;
  border: 0.1rem solid ${({ theme }) => theme.baseColors.grayscale[400]};
  border-radius: 0.8rem;
`;

const CheckboxContainer = styled.div`
  margin-top: 0.4rem;
`;

const ApplicantList = styled.ul`
  width: 100%;
  flex: 1;

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
  TitleBox,
  ApplicantNumber,
  TitleContainer,
  CheckboxContainer,
  ApplicantList,
};

export default S;
