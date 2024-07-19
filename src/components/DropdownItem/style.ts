import styled from '@emotion/styled';

const Item = styled.button<{ isHighlight: boolean }>`
  display: block;
  text-align: left;
  width: 100%;

  padding: 12px 16px;
  ${({ theme }) => theme.typography.common.default};
  color: ${({ isHighlight, theme }) => (isHighlight ? theme.baseColors.redscale[500] : 'none')};
  border-radius: 4px;

  cursor: pointer;
  transition: all 0.2s ease-in-out;

  &:hover {
    background-color: ${({ isHighlight, theme }) =>
      isHighlight ? theme.baseColors.redscale[300] : theme.baseColors.grayscale[300]};
    color: ${({ isHighlight, theme }) => (isHighlight ? theme.baseColors.grayscale[50] : 'none')};
  }
`;

const S = {
  Item,
};

export default S;
