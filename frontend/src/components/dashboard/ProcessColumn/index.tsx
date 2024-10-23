import { useMemo } from 'react';
import { useParams } from 'react-router-dom';

import CheckBox from '@components/_common/atoms/CheckBox';
import { DropdownItemType } from '@components/_common/molecules/DropdownItemRenderer';
import { Process } from '@customTypes/process';

import useApplicant from '@hooks/useApplicant';
import { SimpleProcess } from '@hooks/useProcess';
import specificApplicant from '@hooks/useSpecificApplicant';

import { DropdownProvider } from '@contexts/DropdownContext';
import { useFloatingEmailForm } from '@contexts/FloatingEmailFormContext';
import { useModal } from '@contexts/ModalContext';
import { useMultiApplicant } from '@contexts/MultiApplicantContext';
import { useSpecificApplicantId } from '@contexts/SpecificApplicnatIdContext';
import { useSpecificProcessId } from '@contexts/SpecificProcessIdContext';

import ApplicantCard from '../ApplicantCard';
import ProcessDescription from './ProcessDescription';
import S from './style';

interface ProcessColumnProps {
  process: Process;
  processList: SimpleProcess[];
  showRejectedApplicant: boolean;
  isPassedColumn: boolean;
  searchedName?: string;
}

export default function ProcessColumn({
  process,
  processList,
  showRejectedApplicant,
  isPassedColumn = false,
  searchedName = '',
}: ProcessColumnProps) {
  const { dashboardId, applyFormId } = useParams() as { dashboardId: string; applyFormId: string };
  const { mutate: moveApplicantProcess } = useApplicant({});
  const { mutate: rejectMutate } = specificApplicant.useRejectApplicant({ dashboardId, applyFormId });

  const { setApplicantId } = useSpecificApplicantId();
  const { setProcessId } = useSpecificProcessId();
  const { open } = useModal();
  const { open: sideEmailFormOpen } = useFloatingEmailForm();
  const {
    isMultiType,
    applicants: selectedApplicantIds,
    addApplicant,
    addApplicants,
    removeApplicant,
    removeApplicants,
  } = useMultiApplicant();
  const menuItemsList = ({ applicantId }: { applicantId: number }) => {
    const menuItems: DropdownItemType[] = [
      {
        type: 'subTrigger',
        id: 'moveProcess',
        name: '단계 이동',
        items: processList
          .filter((simpleProcess) => simpleProcess.processId !== process.processId)
          .map(({ processName, processId }) => ({
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

  const isAllApplicantsChecked = () => {
    const filteredApplicantsIds = filteredApplicants.map((applicant) => applicant.applicantId);
    return filteredApplicantsIds.length > 0 && filteredApplicantsIds.every((id) => selectedApplicantIds.includes(id));
  };

  const processSelectHandler = (isChecked: boolean) => {
    const filteredApplicantsIds = filteredApplicants.map((applicant) => applicant.applicantId);

    if (isChecked) {
      addApplicants(filteredApplicantsIds);
    } else {
      removeApplicants(filteredApplicantsIds);
    }
  };

  return (
    <S.ProcessWrapper isPassedColumn={isPassedColumn}>
      <S.Header>
        <S.TitleContainer>
          <S.TitleBox>
            <S.Title>{process.name}</S.Title>
            <S.ApplicantNumber>{filteredApplicants.length}</S.ApplicantNumber>
          </S.TitleBox>
          {isMultiType && (
            <S.CheckboxContainer>
              <CheckBox
                isChecked={isAllApplicantsChecked()}
                onToggle={(isChecked: boolean) => processSelectHandler(isChecked)}
                isDisabled={filteredApplicants.length === 0}
              />
            </S.CheckboxContainer>
          )}
        </S.TitleContainer>
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
                isSelected={selectedApplicantIds.includes(applicantId)}
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
