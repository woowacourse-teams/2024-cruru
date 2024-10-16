import { ProcessFilterOptions, ProcessSortOptions } from '@customTypes/process';
import { createContext, PropsWithChildren, useCallback, useContext, useMemo, useState } from 'react';

interface ProcessQueryParamsContextProps {
  sortOption: ProcessSortOptions;
  filterOption: ProcessFilterOptions;
  updateSortOption: (sortOption: ProcessSortOptions) => void;
  updateFilterOption: (filterOption: ProcessFilterOptions) => void;
}

const ProcessQueryParamsContext = createContext<ProcessQueryParamsContextProps | null>(null);

export function ProcessQueryParamsProvider({ children }: PropsWithChildren) {
  const [sortOption, setSortOption] = useState<ProcessSortOptions>({
    sortByCreatedAt: 'DESC',
    sortByScore: 'DESC',
  });
  const [filterOption, setFilterOption] = useState<ProcessFilterOptions>({
    evaluationStatus: 'ALL',
    minScore: undefined,
    maxScore: undefined,
  });

  const updateSortOption = useCallback((newSortOption: ProcessSortOptions) => {
    setSortOption(newSortOption);
  }, []);

  const updateFilterOption = useCallback((newFilterOption: ProcessFilterOptions) => {
    setFilterOption(newFilterOption);
  }, []);

  const obj = useMemo(
    () => ({
      sortOption,
      filterOption,
      updateSortOption,
      updateFilterOption,
    }),
    [sortOption, filterOption, updateSortOption, updateFilterOption],
  );

  return <ProcessQueryParamsContext.Provider value={obj}>{children}</ProcessQueryParamsContext.Provider>;
}

export const useProcessQueryParams = () => {
  const context = useContext(ProcessQueryParamsContext);
  if (!context) {
    throw new Error('useProcessQueryParams은 ProcessQueryParamsProvider내부에서 관리되어야 합니다.');
  }
  return context;
};
