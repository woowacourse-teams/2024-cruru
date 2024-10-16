import styled from '@emotion/styled';

const SliderContainer = styled.div`
  position: relative;
  width: 100%;
  height: 2rem;
`;

const SliderTrack = styled.div`
  position: absolute;
  top: 50%;
  transform: translateY(-50%);

  width: 100%;
  height: 0.6rem;
  border-radius: 0.3rem;

  background-color: ${({ theme }) => theme.baseColors.grayscale[300]};
`;

const SliderRange = styled.div<{ left: number; right: number; isDisabled: boolean }>`
  position: absolute;
  top: 50%;
  left: ${({ left }) => `${left}%`};
  right: ${({ right }) => `${right}%`};
  transform: translateY(-50%);

  height: 0.6rem;
  border-radius: 0.3rem;
  background-color: ${({ theme, isDisabled }) =>
    isDisabled ? theme.baseColors.grayscale[400] : theme.baseColors.purplescale[200]};
`;

const SliderThumb = styled.input<{ isDisabled: boolean }>`
  position: absolute;
  top: 50%;
  width: 100%;
  height: 0;
  background: transparent;
  pointer-events: none;

  -webkit-appearance: none;
  appearance: none;

  &::-moz-range-thumb {
    width: 1rem;
    aspect-ratio: 1/1;
    background: ${({ theme }) => theme.baseColors.grayscale[50]};
    cursor: ${({ isDisabled }) => (isDisabled ? 'default' : 'pointer')};
    pointer-events: auto;

    border: 0.4rem solid
      ${({ theme, isDisabled }) => (isDisabled ? theme.baseColors.grayscale[600] : theme.baseColors.purplescale[700])};
    border-radius: 100%;
  }

  &::-webkit-slider-thumb {
    width: 1.6rem;
    aspect-ratio: 1/1;
    background: ${({ theme }) => theme.baseColors.grayscale[50]};
    cursor: ${({ isDisabled }) => (isDisabled ? 'default' : 'pointer')};
    border-radius: 100%;
    pointer-events: auto;

    border: 0.4rem solid
      ${({ theme, isDisabled }) => (isDisabled ? theme.baseColors.grayscale[600] : theme.baseColors.purplescale[700])};
    border-radius: 100%;

    -webkit-appearance: none;
    appearance: none;
  }
`;

const S = {
  SliderContainer,
  SliderTrack,
  SliderRange,
  SliderThumb,
};

export default S;
