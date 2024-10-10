import useProcess, { SimpleProcess } from '@hooks/useProcess';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

export default function usePaginatedEvaluation(currentApplicantProcessId?: number) {
  const { dashboardId, applyFormId } = useParams() as { dashboardId: string; applyFormId: string };
  const { processList } = useProcess({ dashboardId, applyFormId });

  const [currentProcess, setCurrentProcess] = useState<SimpleProcess>({} as SimpleProcess);

  const currentIndex = processList?.findIndex((p) => p.processId === currentProcess.processId);
  const isLastProcess = currentIndex === processList.length - 1;
  const isFirstProcess = currentIndex === 0;
  const isCurrentProcess = currentProcess?.processId === currentApplicantProcessId;

  const moveProcess = (direction: 1 | -1) => {
    if (currentIndex < 0) return;
    if ((direction === 1 && isLastProcess) || (direction === -1 && isFirstProcess)) return;

    const nextIndex = currentIndex + direction;
    setCurrentProcess(processList[nextIndex]);
  };

  useEffect(() => {
    setCurrentProcess(processList.find((p) => p.processId === currentApplicantProcessId) || ({} as SimpleProcess));
  }, [currentApplicantProcessId, JSON.stringify(processList)]);

  return { currentProcess, isCurrentProcess, isLastProcess, isFirstProcess, moveProcess };
}
