import { createContext, useContext, useState, ReactNode, useMemo } from 'react';

interface FloatingEmailFormContext {
  isOpen: boolean;
  open: () => void;
  close: () => void;
}

const FloatingEmailFormContext = createContext<FloatingEmailFormContext | undefined>(undefined);

export function FloatingEmailFormProvider({ children }: { children: ReactNode }) {
  const [isOpen, setIsOpen] = useState(false);

  const open = () => setIsOpen(true);
  const close = () => setIsOpen(false);

  const obj = useMemo(() => ({ isOpen, open, close }), [isOpen]);

  return <FloatingEmailFormContext.Provider value={obj}>{children}</FloatingEmailFormContext.Provider>;
}

export const useFloatingEmailForm = (): FloatingEmailFormContext => {
  const context = useContext(FloatingEmailFormContext);
  if (!context) {
    throw new Error('useFloatingEmailForm은 FloatingEmailFormProvider내부에서 관리되어야 합니다.');
  }
  return context;
};
