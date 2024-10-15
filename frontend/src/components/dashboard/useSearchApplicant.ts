import useDebounce from '@hooks/utils/useDebounce';
import { useCallback, useState } from 'react';

export const useSearchApplicant = () => {
  const [name, setName] = useState('');

  const handleName = useCallback((newName: string) => {
    setName(newName);
  }, []);

  const debouncedName = useDebounce(name, 300);

  return {
    name,
    debouncedName,
    handleName,
  };
};
