import { useParams } from 'react-router-dom';

import { Process } from '@customTypes/process';
import { DropdownItemType } from '@components/_common/molecules/DropdownItemRenderer';

import useProcess from '@hooks/useProcess';
import useApplicant from '@hooks/useApplicant';
import specificApplicant from '@hooks/useSpecificApplicant';

import { useSpecificApplicantId } from '@contexts/SpecificApplicnatIdContext';
import { useSpecificProcessId } from '@contexts/SpecificProcessIdContext';
import { useModal } from '@contexts/ModalContext';
import { DropdownProvider } from '@contexts/DropdownContext';
import { useFloatingEmailForm } from '@contexts/FloatingEmailFormContext';
import { useMultiApplicant } from '@contexts/MultiApplicantContext';

import { useMemo } from 'react';
import ApplicantCard from '../ApplicantCard';
import ProcessDescription from './ProcessDescription';
import S from './style';

interface ProcessColumnProps {
  process: Process;
  showRejectedApplicant: boolean;
  isPassedColumn: boolean;
  searchedName?: string;
}

export default function ProcessColumn({
  process,
  showRejectedApplicant,
  isPassedColumn = false,
  searchedName = '',
}: ProcessColumnProps) {
  const { dashboardId, applyFormId } = useParams() as { dashboardId: string; applyFormId: string };
  const { processList } = useProcess({ dashboardId, applyFormId });
  const { mutate: moveApplicantProcess } = useApplicant({});
  const { mutate: rejectMutate } = specificApplicant.useRejectApplicant({ dashboardId, applyFormId });

  const { setApplicantId } = useSpecificApplicantId();
  const { setProcessId } = useSpecificProcessId();
  const { open } = useModal();
  const { open: sideEmailFormOpen } = useFloatingEmailForm();
  const { isMultiType, applicants, addApplicant, removeApplicant } = useMultiApplicant();

  const menuItemsList = ({ applicantId }: { applicantId: number }) => {
    const menuItems: DropdownItemType[] = [
      {
        type: 'subTrigger',
        id: 'moveProcess',
        name: '단계 이동',
        items: processList.map(({ processName, processId }) => ({
          type: 'clickable',
          id: processId,
          name: processName,
          onClick: ({ targetProcessId }) => {
            moveApplicantProcess({ processId: targetProcessId, applicants: [applicantId] });
          },
        })),
      },
      {
        type: 'clickable',
        id: 'emailButton',
        name: '이메일 보내기',
        hasSeparate: true,
        onClick: () => {
          setApplicantId(applicantId);
          sideEmailFormOpen();
        },
      },
      {
        type: 'clickable',
        id: 'rejectButton',
        name: '불합격 처리',
        isHighlight: true,
        hasSeparate: true,
        onClick: () => {
          rejectMutate({ applicantId });
        },
      },
    ];

    return menuItems;
  };

  const applicantSelectHandler = (id: number, isChecked: boolean) => {
    if (isChecked) {
      addApplicant(id);
    } else {
      removeApplicant(id);
    }
  };

  const cardClickHandler = (id: number) => {
    setApplicantId(id);
    setProcessId(process.processId);
    open();
  };

  const filteredApplicants = useMemo(
    () =>
      process.applicants.filter(({ applicantName, isRejected }) => {
        const matchesName = searchedName ? applicantName.includes(searchedName) : true;
        const matchesRejection = showRejectedApplicant === isRejected;
        return matchesName && matchesRejection;
      }),
    [searchedName, showRejectedApplicant, process.applicants],
  );

  return (
    <S.ProcessWrapper isPassedColumn={isPassedColumn}>
      <S.Header>
        <S.Title>{process.name}</S.Title>
        <ProcessDescription description={process.description} />
      </S.Header>
      <S.ApplicantList>
        {filteredApplicants.map(
          ({ applicantId, applicantName, createdAt, evaluationCount, averageScore, isRejected }) => (
            <DropdownProvider key={applicantId}>
              <ApplicantCard
                name={applicantName}
                isRejected={isRejected}
                createdAt={createdAt}
                evaluationCount={evaluationCount}
                averageScore={averageScore}
                popOverMenuItems={menuItemsList({ applicantId })}
                isSelectMode={isMultiType}
                isSelected={applicants.includes(applicantId)}
                onCardClick={() => cardClickHandler(applicantId)}
                onSelectApplicant={(isChecked: boolean) => applicantSelectHandler(applicantId, isChecked)}
              />
            </DropdownProvider>
          ),
        )}
      </S.ApplicantList>
    </S.ProcessWrapper>
  );
}
