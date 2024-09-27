import { createContext, useContext, useState, ReactNode, useMemo } from 'react';

interface SpecificProcessIdContext {
  setProcessId: (Id: number) => void;
  processId: number | undefined;
}

const SpecificProcessIdContext = createContext<SpecificProcessIdContext | undefined>(undefined);

export function SpecificProcessIdProvider({ children }: { children: ReactNode }) {
  const [processId, setProcessId] = useState<number | undefined>(undefined);

  const obj = useMemo(() => ({ setProcessId: (id: number) => setProcessId(id), processId }), [processId]);

  return <SpecificProcessIdContext.Provider value={obj}>{children}</SpecificProcessIdContext.Provider>;
}

export const useSpecificProcessId = (): SpecificProcessIdContext => {
  const context = useContext(SpecificProcessIdContext);
  if (!context) {
    throw new Error('useSpecificProcessId 훅은 SpecificProcessIdProvider 내부에서 사용되어야 합니다.');
  }
  return context;
};
