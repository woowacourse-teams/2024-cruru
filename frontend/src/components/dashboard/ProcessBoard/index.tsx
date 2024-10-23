import type { Process } from '@customTypes/process';
import type { SimpleProcess } from '@hooks/useProcess';

import ApplicantModal from '@components/ApplicantModal';
import useSortApplicant from '@hooks/useProcess/useSortApplicant';
import useFilterApplicant from '@hooks/useProcess/useFilterApplicant';
import DashboardFunctionTab from '../DashboardFunctionTab';
import ProcessColumn from '../ProcessColumn';
import SideFloatingMessageForm from '../SideFloatingMessageForm';

import S from './style';
import { useSearchApplicant } from '../useSearchApplicant';

interface ProcessBoardProps {
  processes: Process[];
  // eslint-disable-next-line react/no-unused-prop-types
  isSubTab?: boolean;
  showRejectedApplicant?: boolean;
  applicantSortDropdownProps?: ReturnType<typeof useSortApplicant>;
  ratingFilterProps?: ReturnType<typeof useFilterApplicant>;
}

export default function ProcessBoard({
  processes,
  isSubTab,
  showRejectedApplicant = false,
  applicantSortDropdownProps,
  ratingFilterProps,
}: ProcessBoardProps) {
  const { debouncedName, name, updateName } = useSearchApplicant();

  const processList: SimpleProcess[] = processes.map((process) => ({
    processId: process.processId,
    processName: process.name,
  }));

  return (
    <S.Container>
      {isSubTab && applicantSortDropdownProps && ratingFilterProps && (
        <DashboardFunctionTab
          processList={processList}
          searchedName={name}
          onSearchName={(newName) => updateName(newName)}
          isRejectedApplicantsTab={showRejectedApplicant}
          applicantSortDropdownProps={applicantSortDropdownProps}
          ratingFilterProps={ratingFilterProps}
        />
      )}

      <S.ColumnWrapper>
        {processes.map((process, index) => (
          <ProcessColumn
            key={process.processId}
            process={process}
            processList={processList}
            showRejectedApplicant={showRejectedApplicant}
            isPassedColumn={!showRejectedApplicant && index === processes.length - 1}
            searchedName={debouncedName}
          />
        ))}

        <ApplicantModal />

        <SideFloatingMessageForm />
      </S.ColumnWrapper>
    </S.Container>
  );
}
