import { css } from '@emotion/react';
import styled from '@emotion/styled';

export interface StyleProps {
  isChecked: boolean;
  isDisabled: boolean;
}

const Switch = styled.div<StyleProps>`
  --parent-width: 5rem;
  --parent-padding: 0.3rem;

  width: var(--parent-width);
  aspect-ratio: 5/3;

  background-color: ${({ isChecked, theme }) =>
    isChecked ? theme.colors.brand.primary : theme.baseColors.grayscale[100]};
  outline: 0.2rem solid
    ${({ isChecked, theme }) => (isChecked ? theme.colors.brand.primary : theme.baseColors.grayscale[700])};
  border-radius: 99rem;

  display: flex;
  align-items: center;

  padding: var(--parent-padding);

  cursor: pointer;
  transition: background-color 0.3s;
  outline-offset: -0.1rem;
  ${({ isDisabled, theme }) =>
    isDisabled &&
    css({
      backgroundColor: theme.colors.text.block,
      outline: `0.2rem solid ${theme.baseColors.grayscale[500]}`,
    })}
`;

const Knob = styled.div<StyleProps>`
  height: 100%;
  aspect-ratio: 1/1;
  background-color: ${({ isChecked, theme }) =>
    isChecked ? theme.baseColors.grayscale[200] : theme.baseColors.grayscale[700]};
  border-radius: 50%;

  transform: ${({ isChecked }) =>
    isChecked ? 'translate(calc(var(--parent-width) - var(--parent-padding)*2 - 100%))' : 'translate(0)'};
  transition: transform 0.2s;

  ${({ isDisabled, theme }) =>
    isDisabled &&
    css({
      backgroundColor: theme.baseColors.grayscale[700],
    })}
`;

const S = {
  Switch,
  Knob,
};

export default S;
