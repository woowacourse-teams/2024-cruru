import { ToastModal } from '@components/common/Toast';
import { createContext, useState, ReactNode, useContext, useMemo, useCallback } from 'react';

type ToastContextType = {
  alert: (message: string) => void;
  error: (message: string) => void;
  success: (message: string) => void;
  primary: (message: string) => void;
};

export type ToastType = 'default' | 'success' | 'error' | 'primary';

const ToastContext = createContext<ToastContextType | null>(null);

export default function ToastProvider({ children }: { children: ReactNode }) {
  const [toastList, setToastList] = useState<{ id: number; type: ToastType; message: string }[]>([]);
  const [nextId, setNextId] = useState(0);

  const removeToast = (id: number) => {
    setToastList((prev) => prev.filter((toast) => toast.id !== id));
  };

  const handleAlert = useCallback(
    (type: ToastType) => (message: string) => {
      const id = nextId;
      setToastList((prev) => [...prev, { id, type, message }]);
      setNextId(nextId + 1);

      setTimeout(() => removeToast(id), 4000);
    },
    [nextId],
  );

  const providerValue = useMemo(
    () => ({
      alert: handleAlert('default'),
      error: handleAlert('error'),
      success: handleAlert('success'),
      primary: handleAlert('primary'),
    }),
    [handleAlert],
  );

  return (
    <ToastContext.Provider value={providerValue}>
      {toastList.map(({ type, message }) => (
        <ToastModal
          type={type}
          message={message}
        />
      ))}
      {children}
    </ToastContext.Provider>
  );
}

export const useToast = () => {
  const value = useContext(ToastContext);

  if (!value) {
    throw new Error('Toast Context가 존재하지 않습니다.');
  }

  return value;
};
