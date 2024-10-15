import { createContext, useContext, useState, ReactNode, useMemo, useCallback } from 'react';

interface RatingRange {
  min: number;
  max: number;
}

export type RatingFilterType = 'All' | 'Pending' | 'InProgress';

interface InitRatingFilterContext {
  ratingRange: RatingRange;
  ratingFilterType: RatingFilterType;
}

interface RatingFilterContext extends InitRatingFilterContext {
  setRatingMinRange: (min: number) => void;
  setRatingMaxRange: (max: number) => void;
  setRatingFilterType: (type: RatingFilterType) => void;
  reset: () => void;
}

const INIT_MIN = 0;
const INIT_MAX = 5;
const INIT_TYPE: RatingFilterType = 'All';

const RatingFilterContext = createContext<RatingFilterContext | InitRatingFilterContext>({
  ratingRange: {
    min: INIT_MIN,
    max: INIT_MAX,
  },
  ratingFilterType: INIT_TYPE,
});

export function RatingFilterProvider({ children }: { children: ReactNode }) {
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

  const obj = useMemo(
    () => ({
      ratingRange,
      ratingFilterType,
      setRatingMinRange,
      setRatingMaxRange,
      setRatingFilterType,
      reset,
    }),
    [ratingRange, ratingFilterType, setRatingMinRange, setRatingMaxRange, setRatingFilterType, reset],
  );

  return <RatingFilterContext.Provider value={obj}>{children}</RatingFilterContext.Provider>;
}

const isRatingFilterContext = (
  context: RatingFilterContext | InitRatingFilterContext,
): context is RatingFilterContext =>
  'setRatingMinRange' in context &&
  'setRatingMaxRange' in context &&
  'setRatingFilterType' in context &&
  'reset' in context;

export const useRatingFilter = (): RatingFilterContext => {
  const context = useContext(RatingFilterContext);
  if (!context || !isRatingFilterContext(context)) {
    throw new Error('useRatingFilter 훅은 RatingFilterProvider 내부에서 사용되어야 합니다.');
  }
  return context;
};
