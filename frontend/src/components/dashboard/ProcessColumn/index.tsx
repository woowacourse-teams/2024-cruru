import { useParams } from 'react-router-dom';

import { useSpecificApplicantId } from '@contexts/SpecificApplicnatIdContext';
import { useSpecificProcessId } from '@contexts/SpecificProcessIdContext';
import { Process } from '@customTypes/process';
import useProcess from '@hooks/useProcess';
import useApplicant from '@hooks/useApplicant';
import { useModal } from '@contexts/ModalContext';

import S from './style';
import ApplicantCard from '../ApplicantCard';
import ProcessDescription from './ProcessDescription/index';

interface ProcessColumnProps {
  process: Process;
  showRejectedApplicant: boolean;
}

export default function ProcessColumn({ process, showRejectedApplicant }: ProcessColumnProps) {
  const { dashboardId, applyFormId } = useParams() as { dashboardId: string; applyFormId: string };
  const { processList } = useProcess({ dashboardId, applyFormId });
  const { mutate: moveApplicantProcess } = useApplicant({});

  const { setApplicantId } = useSpecificApplicantId();
  const { setProcessId } = useSpecificProcessId();
  const { open } = useModal();

  const menuItemsList = ({ applicantId }: { applicantId: number }) =>
    processList.map(({ processName, processId }) => ({
      id: processId,
      name: processName,
      onClick: ({ targetProcessId }: { targetProcessId: number }) => {
        moveApplicantProcess({ processId: targetProcessId, applicants: [applicantId] });
      },
    }));

  const cardClickHandler = (id: number) => {
    setApplicantId(id);
    setProcessId(process.processId);
    open();
  };

  return (
    <S.ProcessWrapper>
      <S.Header>
        <S.Title>{process.name}</S.Title>
        <ProcessDescription description={process.description} />
      </S.Header>
      <S.ApplicantList>
        {process.applicants
          .filter(({ isRejected }) => (showRejectedApplicant ? isRejected : !isRejected))
          .map(({ applicantId, applicantName, createdAt, evaluationCount, averageScore, isRejected }) => (
            <ApplicantCard
              key={applicantId}
              name={applicantName}
              isRejected={isRejected}
              createdAt={createdAt}
              evaluationCount={evaluationCount}
              averageScore={averageScore}
              popOverMenuItems={menuItemsList({ applicantId })}
              onCardClick={() => cardClickHandler(applicantId)}
            />
          ))}
      </S.ApplicantList>
    </S.ProcessWrapper>
  );
}
