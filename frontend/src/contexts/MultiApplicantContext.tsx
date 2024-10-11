import { createContext, useContext, useState, PropsWithChildren, useMemo, useCallback } from 'react';

interface MultiApplicantContextProps {
  isMultiType: boolean;
  applicants: number[];
  toggleIsMultiType: () => void;

  addApplicant: (applicantId: number) => void;
  removeApplicant: (applicantId: number) => void;
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

  const addApplicant = (applicantId: number) => {
    setApplicants((prev) => [...prev, applicantId]);
  };

  const removeApplicant = useCallback(
    () => (applicantId: number) => {
      if (applicants.includes(applicantId)) return;
      setApplicants((prev) => {
        const temp = [...prev];
        const index = prev.findIndex((id) => id === applicantId);
        temp.splice(index, 1);
        return temp;
      });
    },
    [applicants],
  );

  const resetApplicants = () => setApplicants([]);

  const obj = useMemo(
    () => ({
      isMultiType: isOpen,
      applicants,
      toggleIsMultiType,
      addApplicant,
      removeApplicant,
      resetApplicants,
    }),
    [isOpen, toggleIsMultiType, removeApplicant],
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
