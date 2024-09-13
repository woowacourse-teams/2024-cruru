import { useState } from 'react';

interface useTabProps<T> {
  defaultValue: T;
}

export default function useTab<T>({ defaultValue }: useTabProps<T>) {
  const [currentMenu, setCurrentMenu] = useState<T>(defaultValue);

  const moveTab = (e: React.MouseEvent<HTMLButtonElement>) => {
    const { name } = e.currentTarget;
    setCurrentMenu(name as T);
  };

  return { currentMenu, moveTab };
}
