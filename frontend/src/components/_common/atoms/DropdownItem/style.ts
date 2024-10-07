import styled from '@emotion/styled';

const Item = styled.button<{ isHighlight: boolean; size: 'sm' | 'md'; hasSeparate: boolean }>`
  display: block;
  text-align: left;
  width: 100%;

  padding: ${({ size }) => (size === 'md' ? '6px 24px' : '6px 12px')};
  ${({ theme, size }) => (size === 'md' ? theme.typography.common.default : theme.typography.common.small)};

  color: ${({ isHighlight, theme }) => (isHighlight ? theme.baseColors.redscale[500] : 'none')};
  border-top: ${({ theme, hasSeparate }) =>
    hasSeparate ? `0.15rem solid ${theme.baseColors.grayscale[400]}` : 'none'};

  cursor: pointer;
  transition: all 0.2s ease-in-out;

  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;

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
