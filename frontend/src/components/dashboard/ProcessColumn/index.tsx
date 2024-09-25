import { useParams } from 'react-router-dom';

import { useSpecificApplicantId } from '@contexts/SpecificApplicnatIdContext';
import { useSpecificProcessId } from '@contexts/SpecificProcessIdContext';
import { Process } from '@customTypes/process';
import useProcess from '@hooks/useProcess';
import useApplicant from '@hooks/useApplicant';
import { useModal } from '@contexts/ModalContext';
import { PopOverMenuItem } from '@customTypes/common';

import specificApplicant from '@hooks/useSpecificApplicant';
import { useFloatingEmailForm } from '@contexts/FloatingEmailFormContext';
import ApplicantCard from '../ApplicantCard';
import ProcessDescription from './ProcessDescription';
import S from './style';

interface ProcessColumnProps {
  process: Process;
  showRejectedApplicant: boolean;
}

export default function ProcessColumn({ process, showRejectedApplicant }: ProcessColumnProps) {
  const { dashboardId, applyFormId } = useParams() as { dashboardId: string; applyFormId: string };
  const { processList } = useProcess({ dashboardId, applyFormId });
  const { mutate: moveApplicantProcess } = useApplicant({});
  const { mutate: rejectMutate } = specificApplicant.useRejectApplicant({ dashboardId, applyFormId });

  const { setApplicantId } = useSpecificApplicantId();
  const { setProcessId } = useSpecificProcessId();
  const { open } = useModal();
  const { open: sideEmailFormOpen } = useFloatingEmailForm();

  const menuItemsList = ({ applicantId }: { applicantId: number }) => {
    const menuItems = processList.map(
      ({ processName, processId }) =>
        ({
          id: processId,
          name: processName,
          onClick: ({ targetProcessId }: { targetProcessId: number }) => {
            moveApplicantProcess({ processId: targetProcessId, applicants: [applicantId] });
          },
        }) as PopOverMenuItem,
    );

    menuItems.push({
      id: 'emailButton',
      name: '이메일 보내기',
      hasSeparate: true,
      onClick: () => {
        setApplicantId(applicantId);
        sideEmailFormOpen();
      },
    });

    menuItems.push({
      id: 'rejectButton',
      name: '불합격 처리',
      isHighlight: true,
      hasSeparate: true,
      onClick: () => {
        rejectMutate({ applicantId });
      },
    });

    return menuItems;
  };

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
