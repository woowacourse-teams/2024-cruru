import styled from '@emotion/styled';

const ToggleWrapper = styled.div`
  display: flex;
  gap: 1.2rem;
  align-items: center;
`;

const ToggleLabel = styled.span`
  ${({ theme }) => theme.typography.heading[200]};
  color: ${({ theme }) => theme.baseColors.grayscale[800]};
`;

const S = {
  ToggleWrapper,
  ToggleLabel,
};

export default S;
