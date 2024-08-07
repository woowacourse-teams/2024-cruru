import styled from '@emotion/styled';
import { hideScrollBar } from '@styles/utils';

const ProcessWrapper = styled.section`
  width: 30rem;
  min-width: 30rem;
  padding: 20px 12px 12px 12px;
  border-radius: 8px;
  background-color: ${({ theme }) => theme.baseColors.grayscale[100]};

  overflow-y: scroll;
  ${hideScrollBar};
`;

const Header = styled.header`
  display: flex;
  justify-content: space-between;

  padding: 8px 4px;
  margin-bottom: 20px;
  border-bottom: 1px solid ${({ theme }) => theme.colors.text.block};
`;

const Title = styled.h2`
  ${({ theme }) => theme.typography.heading[500]};
  padding: 4px;
`;

const ApplicantList = styled.ul`
  width: 100%;
  display: flex;
  flex-direction: column;
  padding: 12px;

  gap: 12px;
`;

const S = {
  ProcessWrapper,
  Header,
  Title,
  ApplicantList,
};

export default S;
