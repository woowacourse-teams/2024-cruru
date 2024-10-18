import { FilterParams } from '@customTypes/process';
import { useCallback, useState } from 'react';

export type FilterOptionState = Omit<FilterParams, 'minScore' | 'maxScore'> & {
  minScore?: number;
  maxScore?: number;
};

interface RatingRange {
  min: number;
  max: number;
}

export type RatingFilterType = 'All' | 'Pending' | 'InProgress';

export const INIT_MIN = 0;
export const INIT_MAX = 5;
export const INIT_TYPE: RatingFilterType = 'All';

export default function useFilterApplicant() {
  const [ratingRange, _setRatingRange] = useState<RatingRange>({
    min: INIT_MIN,
    max: INIT_MAX,
  });
  const [ratingFilterType, _setRatingFilterType] = useState<RatingFilterType>(INIT_TYPE);

  const setRatingMinRange = useCallback((min: number) => _setRatingRange((props) => ({ ...props, min })), []);
  const setRatingMaxRange = useCallback((max: number) => _setRatingRange((props) => ({ ...props, max })), []);
  const setRatingFilterType = useCallback((type: RatingFilterType) => _setRatingFilterType(type), []);
  const reset = useCallback(() => {
    _setRatingRange({ min: INIT_MIN, max: INIT_MAX });
    _setRatingFilterType(INIT_TYPE);
  }, []);

  return {
    ratingRange,
    ratingFilterType,
    setRatingMinRange,
    setRatingMaxRange,
    setRatingFilterType,
    reset,
  };
}
