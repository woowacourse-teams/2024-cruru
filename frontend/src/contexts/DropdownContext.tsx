import { createContext, useContext, useState, useMemo, PropsWithChildren } from 'react';

interface DropdownContext {
  isOpen: boolean;
  open: () => void;
  close: () => void;
  selected?: string;
  setSelected: (name: string) => void;
}

const DropdownContext = createContext<DropdownContext | undefined>(undefined);

interface DropdownProviderProps extends PropsWithChildren {
  initValue?: string;
}

export function DropdownProvider({ initValue, children }: DropdownProviderProps) {
  const [isOpen, setIsOpen] = useState(false);
  const [selected, _setSelected] = useState(initValue);

  const open = () => setIsOpen(true);
  const close = () => setIsOpen(false);
  const setSelected = (name: string) => _setSelected(name);

  const obj = useMemo(() => ({ isOpen, open, close, selected, setSelected }), [isOpen, selected]);

  return <DropdownContext.Provider value={obj}>{children}</DropdownContext.Provider>;
}

export const useDropdown = (): DropdownContext => {
  const context = useContext(DropdownContext);
  if (!context) {
    throw new Error('useDropdown은 DropdownProvider내부에서 관리되어야 합니다.');
  }
  return context;
};
