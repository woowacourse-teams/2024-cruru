import styled from '@emotion/styled';
import { hideScrollBar } from '@styles/utils';

const ProcessWrapper = styled.section`
  width: 100%;
  min-width: 25rem;
  max-width: 25rem;

  height: 100%;
  padding: 1.2rem;
  border-radius: 0.8rem;
  border: 0.1rem solid ${({ theme }) => theme.baseColors.grayscale[400]};
  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};

  overflow-y: scroll;
  overflow-x: visible;
  ${hideScrollBar};
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

const Title = styled.h2`
  ${({ theme }) => theme.typography.heading[500]};
  padding: 0.4rem;
`;

const ApplicantList = styled.ul`
  width: 100%;
  display: flex;
  flex-direction: column;
  padding: 0.4rem;

  gap: 1.2rem;
`;

const S = {
  ProcessWrapper,
  Header,
  Title,
  ApplicantList,
};

export default S;
