import type { Process } from '@customTypes/process';
import type { SimpleProcess } from '@hooks/useProcess';

import ApplicantModal from '@components/ApplicantModal';
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
}

export default function ProcessBoard({ processes, isSubTab, showRejectedApplicant = false }: ProcessBoardProps) {
  const { debouncedName, name, updateName } = useSearchApplicant();

  const processList: SimpleProcess[] = processes.map((process) => ({
    processId: process.processId,
    processName: process.name,
  }));

  return (
    <S.Container>
      {isSubTab && (
        <DashboardFunctionTab
          processList={processList}
          searchedName={name}
          onSearchName={(newName) => updateName(newName)}
        />
      )}

      <S.ColumnWrapper>
        {processes.map((process, index) => (
          <ProcessColumn
            key={process.processId}
            process={process}
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
