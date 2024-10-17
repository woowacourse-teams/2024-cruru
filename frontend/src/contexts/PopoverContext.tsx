/* eslint-disable react/jsx-no-constructed-context-values */
import { createContext, PropsWithChildren, useContext } from 'react';

interface PopoverContextProps {
  close: () => void;
}

const PopoverContext = createContext<PopoverContextProps | undefined>(undefined);

export const usePopover = (): PopoverContextProps => {
  const context = useContext(PopoverContext);
  if (!context) {
    throw new Error('usePopover은  Popover내부에서 사용되어야 합니다.');
  }
  return context;
};

interface PopoverProviderProps extends PropsWithChildren {
  onClose: () => void;
}

export function PopoverProvider({ onClose, children }: PopoverProviderProps) {
  return <PopoverContext.Provider value={{ close: onClose }}>{children}</PopoverContext.Provider>;
}
