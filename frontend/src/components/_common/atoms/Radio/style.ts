import { css } from '@emotion/react';
import styled from '@emotion/styled';

const RadioContainer = styled.div`
  display: inline-flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
`;

const RadioOuter = styled.div<{ diameter?: string; checked: boolean; isDisabled: boolean }>`
  height: ${({ diameter }) => diameter || '1.6rem'};
  aspect-ratio: 1/1;
  border: 0.1rem solid
    ${({ checked, theme }) => (checked ? theme.colors.brand.primary : theme.baseColors.grayscale[800])};
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;

  ${({ isDisabled, theme }) =>
    isDisabled &&
    css`
      border-color: ${theme.baseColors.grayscale[500]};
      background-color: ${theme.baseColors.grayscale[400]};
    `}

  transition: all 0.2s ease; //TODO: theme hover로 바꿔야 합니다.
`;

const RadioInner = styled.div<{ checked: boolean; isDisabled: boolean }>`
  height: 60%;
  aspect-ratio: 1/1;
  background-color: ${({ checked, theme }) => (checked ? theme.colors.brand.primary : theme.baseColors.grayscale[800])};
  border-radius: 50%;

  ${({ isDisabled, theme }) =>
    isDisabled &&
    css`
      background-color: ${theme.baseColors.grayscale[500]};
    `}

  opacity: ${({ checked }) => (checked ? '1' : '0')};
  transition: all 0.2s ease; //TODO: theme hover로 바꿔야 합니다.
`;

const HiddenRadio = styled.input`
  border: 0;
  clip: rect(0 0 0 0);
  clip-path: inset(50%);
  height: 1px;
  margin: -1px;
  overflow: hidden;
  padding: 0;
  position: absolute;
  white-space: nowrap;
  width: 1px;
`;

const S = {
  RadioContainer,
  RadioOuter,
  RadioInner,
  HiddenRadio,
};

export default S;
