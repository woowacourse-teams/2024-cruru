/* eslint-disable react/jsx-one-expression-per-line */
import { useState } from 'react';
import RadioLabelField from '@components/_common/molecules/RadioLabelField';
import Slider from '@components/_common/atoms/Slider';
import Button from '@components/_common/atoms/Button';
import { usePopover } from '@contexts/PopoverContext';
import { HiStar } from 'react-icons/hi';
import useFilterApplicant, {
  INIT_MAX,
  INIT_MIN,
  INIT_TYPE,
  RatingFilterType,
} from '@hooks/useProcess/useFilterApplicant';
import S from './style';

export default function RatingFilter({
  ratingFilterType,
  ratingRange,
  setRatingFilterType,
  setRatingMaxRange,
  setRatingMinRange,
}: ReturnType<typeof useFilterApplicant>) {
  const [currentRatingFilterType, setCurrentRatingFilterType] = useState<typeof ratingFilterType>(ratingFilterType);
  const [currentRatingRangeMin, setCurrentRatingRangeMin] = useState<number>(ratingRange.min);
  const [currentRatingRangeMax, setCurrentRatingRangeMax] = useState<number>(ratingRange.max);

  const { close } = usePopover();

  const handleRangeChange = (min: number, max: number) => {
    setCurrentRatingRangeMax(max);
    setCurrentRatingRangeMin(min);
  };

  const sliderProps = {
    min: INIT_MIN,
    max: INIT_MAX,
    step: 0.5,
    minValue: currentRatingRangeMin,
    maxValue: currentRatingRangeMax,
    isDisabled: currentRatingFilterType === 'Pending',
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
    if (currentRatingFilterType === 'Pending') {
      setRatingMaxRange(INIT_MAX);
      setRatingMinRange(INIT_MIN);
    } else {
      setRatingMaxRange(currentRatingRangeMax);
      setRatingMinRange(currentRatingRangeMin);
    }
    setRatingFilterType(currentRatingFilterType);
    close();
  };

  const handleResetClick = () => {
    setCurrentRatingFilterType(INIT_TYPE);
    setCurrentRatingRangeMin(INIT_MIN);
    setCurrentRatingRangeMax(INIT_MAX);

    setRatingFilterType(INIT_TYPE);
    setRatingMinRange(INIT_MIN);
    setRatingMaxRange(INIT_MAX);

    close();
  };

  return (
    <S.Wrapper>
      <S.RangeWrapper>
        <S.RangeLabel>
          <div>평점 범위</div>
          <S.RatingNumbers>
            <HiStar size={16} />
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
