import styled from '@emotion/styled';

const CSVButton = styled.button`
  height: 3.4rem;
  border-radius: 0.8rem;
  padding: 0.4rem 0.8rem;

  ${({ theme }) => theme.typography.heading[200]};

  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  gap: 0.4rem;

  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};
  border: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
  border-radius: 0.8rem;

  transition: background-color 0.2s ease-in-out;

  &:hover {
    background-color: ${({ theme }) => theme.baseColors.grayscale[100]};
  }
`;

export default {
  CSVButton,
};
