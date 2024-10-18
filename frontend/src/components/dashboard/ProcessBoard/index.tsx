import { useState } from 'react';
import type { Process } from '@customTypes/process';
import type { SimpleProcess } from '@hooks/useProcess';

import ApplicantModal from '@components/ApplicantModal';
import DashboardFunctionTab from '../DashboardFunctionTab';
import ProcessColumn from '../ProcessColumn';
import SideFloatingMessageForm from '../SideFloatingMessageForm';

import S from './style';

interface ProcessBoardProps {
  processes: Process[];
  // eslint-disable-next-line react/no-unused-prop-types
  isSubTab?: boolean;
  showRejectedApplicant?: boolean;
}

export default function ProcessBoard({ processes, showRejectedApplicant = false }: ProcessBoardProps) {
  const [searchedName, setSearchedName] = useState<string>('');

  const processList: SimpleProcess[] = processes.map((process) => ({
    processId: process.processId,
    processName: process.name,
  }));

  const handleSearchName = (name: string) => {
    setSearchedName(name);
  };

  return (
    <S.Container>
      <DashboardFunctionTab
        processList={processList}
        onSearchName={(name) => handleSearchName(name)}
      />

      <S.ColumnWrapper>
        {processes.map((process, index) => (
          <ProcessColumn
            key={process.processId}
            process={process}
            showRejectedApplicant={showRejectedApplicant}
            isPassedColumn={!showRejectedApplicant && index === processes.length - 1}
            searchedName={searchedName}
          />
        ))}

        <ApplicantModal />

        <SideFloatingMessageForm />
      </S.ColumnWrapper>
    </S.Container>
  );
}
