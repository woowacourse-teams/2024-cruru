import { ProcessSortOption } from '@customTypes/process';
import { useCallback, useState } from 'react';

export default function useSortApplicant() {
  const [sortOption, setSortOption] = useState<ProcessSortOption | undefined>();

  const updateSortOption = useCallback((option?: ProcessSortOption) => {
    setSortOption(option);
  }, []);

  return {
    sortOption,
    updateSortOption,
  };
}
