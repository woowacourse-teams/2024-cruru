import React, { useState } from 'react';
import S from './style';

interface SliderProps {
  min: number;
  max: number;
  step: number;
  initialMin: number;
  initialMax: number;
  isDisabled?: boolean;
  onRangeChange: (min: number, max: number) => void;
}

export default function Slider({
  min,
  max,
  step,
  initialMin,
  initialMax,
  isDisabled = false,
  onRangeChange,
}: SliderProps) {
  const [minValue, setMinValue] = useState(initialMin);
  const [maxValue, setMaxValue] = useState(initialMax);

  const handleMinChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (!isDisabled) {
      const newMinValue = Math.min(Number(e.target.value), Number(maxValue - step));
      if (newMinValue !== minValue) {
        setMinValue(newMinValue);
        onRangeChange(newMinValue, maxValue);
      }
    }
  };

  const handleMaxChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (!isDisabled) {
      const newMaxValue = Math.max(Number(e.target.value), Number(minValue + step));
      if (newMaxValue !== maxValue) {
        setMaxValue(newMaxValue);
        onRangeChange(minValue, newMaxValue);
      }
    }
  };

  const sliderRangeLeft = ((minValue - min) / (max - min)) * 100;
  const sliderRangeRight = 100 - ((maxValue - min) / (max - min)) * 100;

  return (
    <S.SliderContainer>
      <S.SliderTrack />
      <S.SliderRange
        left={sliderRangeLeft}
        right={sliderRangeRight}
        isDisabled={isDisabled}
      />
      <S.SliderThumb
        type="range"
        min={min}
        max={max}
        step={step}
        value={minValue}
        onChange={handleMinChange}
        isDisabled={isDisabled}
      />
      <S.SliderThumb
        type="range"
        min={min}
        max={max}
        step={step}
        value={maxValue}
        onChange={handleMaxChange}
        isDisabled={isDisabled}
      />
    </S.SliderContainer>
  );
}
