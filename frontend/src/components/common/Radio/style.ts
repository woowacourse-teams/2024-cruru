import styled from '@emotion/styled';

const RadioContainer = styled.div`
  display: inline-flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
`;

const RadioOuter = styled.div<{ diameter?: number; checked: boolean }>`
  height: ${({ diameter }) => (diameter ? `${diameter}px` : '1.6rem')};
  aspect-ratio: 1/1;
  border: 2px solid ${({ checked, theme }) => (checked ? theme.colors.brand.primary : theme.baseColors.grayscale[800])};
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;

  transition: all 0.2s ease; //TODO: theme hover로 바꿔야 합니다.
`;

const RadioInner = styled.div<{ diameter?: number; checked: boolean }>`
  height: ${({ diameter }) => (diameter ? `${diameter / 2}px` : '0.8rem')};
  aspect-ratio: 1/1;
  background-color: ${({ checked, theme }) => (checked ? theme.colors.brand.primary : theme.baseColors.grayscale[800])};
  border-radius: 50%;

  opacity: ${({ checked }) => (checked ? '1' : '0')};
  transition: all 0.2s ease; //TODO: theme hover로 바꿔야 합니다.
`;

const S = {
  RadioContainer,
  RadioOuter,
  RadioInner,
};

export default S;
