/* eslint-disable react/jsx-one-expression-per-line */
import RadioLabelField from '@components/_common/molecules/RadioLabelField';
import Slider from '@components/_common/atoms/Slider';
import { useRatingFilter } from '@contexts/RatingFilterContext';
import type { RatingFilterType } from '@contexts/RatingFilterContext';
import Button from '@components/_common/atoms/Button';
import { useState } from 'react';
import S from './style';

export default function RatingFilter() {
  const { ratingFilterType, ratingRange, setRatingFilterType, setRatingMaxRange, setRatingMinRange, reset } =
    useRatingFilter();

  const [currentRatingFilterType, setCurrentRatingFilterType] = useState<typeof ratingFilterType>(ratingFilterType);
  const [currentRatingRangeMin, setCurrentRatingRangeMin] = useState<number>(ratingRange.min);
  const [currentRatingRangeMax, setCurrentRatingRangeMax] = useState<number>(ratingRange.max);

  const handleRangeChange = (min: number, max: number) => {
    setCurrentRatingRangeMax(max);
    setCurrentRatingRangeMin(min);
  };

  const sliderProps = {
    min: 0,
    max: 5,
    step: 0.5,
    initialMin: 0,
    initialMax: 5,
  };

  const handleRadioClick = (type: RatingFilterType) => {
    setCurrentRatingFilterType(type);
  };

  const radioLabelOptions = [
    { optionLabel: '전체 선택', isChecked: currentRatingFilterType === 'All', onToggle: () => handleRadioClick('All') },
    {
      optionLabel: '평가 없음',
      isChecked: currentRatingFilterType === 'Pending',
      onToggle: () => handleRadioClick('Pending'),
    },
    {
      optionLabel: '평가 진행/완료',
      isChecked: currentRatingFilterType === 'InProgress',
      onToggle: () => handleRadioClick('InProgress'),
    },
  ];

  const handleApplyClick = () => {
    setRatingFilterType(currentRatingFilterType);
    setRatingMaxRange(currentRatingRangeMax);
    setRatingMinRange(currentRatingRangeMin);
  };

  const handleResetClick = () => {
    reset();
  };

  return (
    <S.Wrapper>
      <S.RangeWrapper>
        <S.RangeLabel>
          <div>평점 범위</div>
          <S.RatingNumbers>
            {currentRatingRangeMin.toFixed(1)} - {currentRatingRangeMax.toFixed(1)}
          </S.RatingNumbers>
        </S.RangeLabel>
        <Slider
          {...sliderProps}
          onRangeChange={handleRangeChange}
        />
      </S.RangeWrapper>
      <S.OptionsWrapper>
        <RadioLabelField
          label="평가 상태"
          options={radioLabelOptions}
        />
      </S.OptionsWrapper>
      <S.ButtonWrapper>
        <Button
          size="sm"
          color="primary"
          onClick={handleApplyClick}
        >
          <S.ButtonInner>필터 적용</S.ButtonInner>
        </Button>
        <Button
          size="sm"
          color="white"
          onClick={handleResetClick}
        >
          <S.ButtonInner>초기화</S.ButtonInner>
        </Button>
      </S.ButtonWrapper>
    </S.Wrapper>
  );
}
