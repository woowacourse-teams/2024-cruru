import { useParams } from 'react-router-dom';

import { useSpecificApplicantId } from '@contexts/SpecificApplicnatIdContext';
import { useSpecificProcessId } from '@contexts/SpecificProcessIdContext';
import { Process } from '@customTypes/process';
import useProcess from '@hooks/useProcess';
import useApplicant from '@hooks/useApplicant';
import { useModal } from '@contexts/ModalContext';

import S from './style';
import ApplicantCard from '../ApplicantCard';

interface ProcessColumnProps {
  process: Process;
}

export default function ProcessColumn({ process }: ProcessColumnProps) {
  const { dashboardId, postId } = useParams() as { dashboardId: string; postId: string };
  const { processList } = useProcess({ dashboardId, postId });
  const { moveApplicantProcess } = useApplicant({});
  const { setApplicantId } = useSpecificApplicantId();
  const { setProcessId } = useSpecificProcessId();
  const { open } = useModal();

  const menuItemsList = ({ applicantId }: { applicantId: number }) =>
    processList.map(({ processName, processId }) => ({
      id: processId,
      name: processName,
      onClick: ({ targetProcessId }: { targetProcessId: number }) => {
        moveApplicantProcess.mutate({ processId: targetProcessId, applicants: [applicantId] });
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
      </S.Header>
      <S.ApplicantList>
        {process.applicants.map(
          ({ applicantId, applicantName, createdAt, isRejected }) =>
            !isRejected && (
              <ApplicantCard
                key={applicantId}
                name={applicantName}
                createdAt={createdAt}
                popOverMenuItems={menuItemsList({ applicantId })}
                onCardClick={() => cardClickHandler(applicantId)}
              />
            ),
        )}
      </S.ApplicantList>
    </S.ProcessWrapper>
  );
}
