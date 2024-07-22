import styled from '@emotion/styled';

const ProcessWrapper = styled.section`
  width: 340px;
  height: 100%;
  padding: 20px 12px 12px 12px;
  border-radius: 8px;
  background-color: ${({ theme }) => theme.baseColors.grayscale[100]};
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
