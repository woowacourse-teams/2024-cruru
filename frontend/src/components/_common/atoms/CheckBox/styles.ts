import { css } from '@emotion/react';
import styled from '@emotion/styled';

interface ContainerProps {
  width?: string | number;
  isChecked: boolean;
  isDisabled?: boolean;
}

interface IconProps {
  isDisabled?: boolean;
}

export type StyleProps = ContainerProps & IconProps;

const widthFormat = (width?: number | string) => {
  if (typeof width === 'number') return `${width}px`;
  return width;
};

const CheckBoxContainer = styled.div<ContainerProps>`
  width: ${({ width }) => widthFormat(width)};
  --parent-width: ${({ width }) => widthFormat(width)};
  aspect-ratio: 1/1;
  border: 0.1rem solid
    ${({ isChecked, theme }) => (isChecked ? theme.baseColors.purplescale[600] : theme.baseColors.grayscale[700])};
  border-radius: 0.4rem;
  background-color: ${({ isChecked, theme }) => (isChecked ? theme.baseColors.purplescale[600] : 'transparent')};

  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;

  cursor: pointer;

  ${({ isDisabled }) => css`
    cursor: ${isDisabled} ? 'not-allowed' : 'pointer';
    opacity: ${isDisabled} ? 0.5 : 1;
    `}

  ${({ isDisabled, theme }) =>
    isDisabled &&
    css`
      background-color: ${theme.baseColors.grayscale[400]};
      border-color: ${theme.baseColors.grayscale[500]};
    `}

  transition:
    background-color 0.2s,
    border-color 0.2s;
`;

const HiddenCheckbox = styled.input`
  position: absolute;
  overflow: hidden;
  width: 1px;
  height: 1px;
  padding: 0;
  border: 0;
  margin: -1px;
  clip: rect(0, 0, 0, 0);
  white-space: nowrap;
  transform: scale(0);
`;

const IconWrapper = styled.div<IconProps>`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  aspect-ratio: 1/1;

  font-size: calc(var(--parent-width) - 0.2rem);
  color: ${({ theme, isDisabled }) => (isDisabled ? theme.baseColors.grayscale[500] : theme.baseColors.grayscale[50])};
`;

const S = {
  CheckBoxContainer,
  HiddenCheckbox,
  IconWrapper,
};

export default S;
