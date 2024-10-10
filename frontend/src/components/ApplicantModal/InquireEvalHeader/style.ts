import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const ProcessNameContainer = styled.div`
  display: flex;
  align-items: center;
  gap: 1rem;
`;

const ProcessName = styled.h3`
  ${({ theme }) => theme.typography.heading[500]};
  ${({ theme }) => theme.colors.text.default};
`;

const CurrentLabel = styled.span`
  padding: 0.2rem 0.8rem;
  border-radius: 1.6rem;
  color: ${({ theme }) => theme.baseColors.bluescale[200]};
  border: 1px solid ${({ theme }) => theme.baseColors.bluescale[200]};
`;

const MoveProcessButton = styled.button``;

const S = {
  Container,
  ProcessNameContainer,
  ProcessName,
  CurrentLabel,
  MoveProcessButton,
};

export default S;
