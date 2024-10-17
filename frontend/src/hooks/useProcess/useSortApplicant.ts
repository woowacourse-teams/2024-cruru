import { SortParams } from '@customTypes/process';
import { useCallback, useState } from 'react';

export type SortOptionState = Partial<SortParams> | undefined;

export default function useSortApplicant() {
  const [sortOption, setSortOption] = useState<SortOptionState>();

  const updateSortOption = useCallback((option?: SortOptionState) => {
    setSortOption(option);
  }, []);

  return {
    sortOption,
    updateSortOption,
  };
}
