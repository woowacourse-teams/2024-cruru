import useDebounce from '@hooks/utils/useDebounce';
import { createContext, useCallback, useContext, useMemo, useState } from 'react';

interface SearchApplicantContextProps {
  name: string;
  debouncedName: string;
  handleName: (name: string) => void;
}

const SearchApplicantContext = createContext<SearchApplicantContextProps | null>(null);

export function SearchApplicantContextProvider({ children }: { children: React.ReactNode }) {
  const [name, setName] = useState('');

  const handleName = useCallback((newName: string) => {
    setName(newName);
  }, []);

  const debouncedName = useDebounce(name, 300);

  const obj = useMemo(
    () => ({
      name,
      debouncedName,
      handleName,
    }),
    [name, debouncedName, handleName],
  );

  return <SearchApplicantContext.Provider value={obj}>{children}</SearchApplicantContext.Provider>;
}

export const useSearchApplicant = () => {
  const context = useContext(SearchApplicantContext);
  if (!context) {
    throw new Error('useSearchApplicant은 SearchApplicantContextProvider 내부에서 관리되어야 합니다.');
  }
  return context;
};
