import styled from '@emotion/styled';
import { hideScrollBar } from '@styles/utils';

const ProcessWrapper = styled.section`
  width: 24rem;
  padding: 20px 12px 12px 12px;
  border-radius: 8px;
  border: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};

  overflow-y: scroll;
  ${hideScrollBar};
`;

const Header = styled.header`
  display: flex;
  justify-content: space-between;

  padding: 8px 4px;
  margin-bottom: 20px;
  border-bottom: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
`;

const Title = styled.h2`
  ${({ theme }) => theme.typography.heading[500]};
  padding: 4px;
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
