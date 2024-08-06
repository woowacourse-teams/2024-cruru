import styled from '@emotion/styled';

const Container = styled.div`
  width: 28.4rem;
  padding: 2.4rem;

  display: flex;
  flex-direction: column;
  gap: 3.6rem;
  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};
`;

const SidebarHeader = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
`;

const Title = styled.h2`
  ${({ theme }) => theme.typography.heading[700]}
`;

const Description = styled.p`
  ${({ theme }) => theme.typography.common.default}
`;

const SidebarContents = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;

  padding: 0 1.6rem;
`;

const StepContainer = styled.div<{ isSelected: boolean }>`
  display: flex;
  align-items: center;
  margin-bottom: 1rem;
  opacity: ${({ isSelected }) => (isSelected ? 1 : 0.5)};
`;

const StepNumber = styled.div<{ isSelected: boolean }>`
  width: 2.4rem;
  aspect-ratio: 1/1;

  display: flex;
  align-items: center;
  justify-content: center;

  border-radius: 50%;
  background-color: ${({ isSelected, theme }) =>
    isSelected ? theme.colors.brand.primary : theme.baseColors.grayscale[400]};
  color: ${({ theme }) => theme.baseColors.grayscale[50]};

  margin-right: 0.6rem;
`;

const StepLabel = styled.div<{ isSelected: boolean }>`
  ${({ theme }) => theme.typography.common.smallAccent}
  color: ${({ isSelected, theme }) => (isSelected ? theme.colors.brand.primary : theme.baseColors.grayscale[400])};
`;

const S = {
  Container,
  SidebarHeader,
  Title,
  Description,
  SidebarContents,
  StepContainer,
  StepNumber,
  StepLabel,
};

export default S;
