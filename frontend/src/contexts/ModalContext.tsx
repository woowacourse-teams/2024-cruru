import { createContext, useContext, useState, ReactNode, useMemo } from 'react';

interface ModalContext {
  isOpen: boolean;
  open: () => void;
  close: () => void;
}

const ModalContext = createContext<ModalContext | undefined>(undefined);

export function ModalProvider({ children }: { children: ReactNode }) {
  const [isOpen, setIsOpen] = useState(false);

  const open = () => setIsOpen(true);
  const close = () => setIsOpen(false);

  const obj = useMemo(() => ({ isOpen, open, close }), [isOpen]);

  return <ModalContext.Provider value={obj}>{children}</ModalContext.Provider>;
}

export const useModal = (): ModalContext => {
  const context = useContext(ModalContext);
  if (!context) {
    throw new Error('useModal은 ModalProvider내부에서 관리되어야 합니다.');
  }
  return context;
};
