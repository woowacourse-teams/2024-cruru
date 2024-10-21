import { createContext, useContext, useState, PropsWithChildren, useMemo, useCallback } from 'react';

interface MultiApplicantContextProps {
  isMultiType: boolean;
  applicants: number[];
  toggleIsMultiType: () => void;

  addApplicant: (applicantId: number) => void;
  addApplicants: (applicantIds: number[]) => void;
  removeApplicant: (applicantId: number) => void;
  removeApplicants: (applicantIds: number[]) => void;
  resetApplicants: () => void;
}

const MultiApplicantContext = createContext<MultiApplicantContextProps | null>(null);

export function MultiApplicantContextProvider({ children }: PropsWithChildren) {
  const [isOpen, setIsOpen] = useState(false);
  const [applicants, setApplicants] = useState<number[]>([]);

  const toggleIsMultiType = useCallback(() => {
    if (isOpen) setIsOpen(false);
    if (!isOpen) setIsOpen(true);
  }, [isOpen]);

  const addApplicant = useCallback(
    (applicantId: number) => {
      if (applicants.includes(applicantId)) return;
      setApplicants((prev) => [...prev, applicantId]);
    },
    [applicants],
  );

  const addApplicants = useCallback((applicantIds: number[]) => {
    setApplicants((prev) => Array.from(new Set([...prev, ...applicantIds])));
  }, []);

  const removeApplicant = useCallback((applicantId: number) => {
    setApplicants((prev) => [...prev].filter((id) => id !== applicantId));
  }, []);

  const removeApplicants = useCallback((applicantIds: number[]) => {
    setApplicants((prev) => [...prev].filter((id) => !applicantIds.includes(id)));
  }, []);

  const resetApplicants = () => setApplicants([]);

  const obj = useMemo(
    () => ({
      isMultiType: isOpen,
      applicants,
      toggleIsMultiType,
      addApplicant,
      addApplicants,
      removeApplicant,
      removeApplicants,
      resetApplicants,
    }),
    [isOpen, toggleIsMultiType, applicants, addApplicants, addApplicant, removeApplicant, removeApplicants],
  );

  return <MultiApplicantContext.Provider value={obj}>{children}</MultiApplicantContext.Provider>;
}

export const useMultiApplicant = () => {
  const context = useContext(MultiApplicantContext);
  if (!context) {
    throw new Error('useMultiApplicant은 MultiApplicantContextProvider내부에서 관리되어야 합니다.');
  }
  return context;
};
