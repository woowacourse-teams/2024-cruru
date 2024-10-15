import ApplicantModal from '@components/ApplicantModal';
import { Process } from '@customTypes/process';

import { useSearchApplicant } from '@contexts/SearchApplicantContext';
import ProcessColumn from '../ProcessColumn';
import SideFloatingMessageForm from '../SideFloatingMessageForm';
import S from './style';

interface KanbanBoardProps {
  processes: Process[];
  // eslint-disable-next-line react/no-unused-prop-types
  isSubTab?: boolean;
  showRejectedApplicant?: boolean;
}

export default function ProcessBoard({ processes, showRejectedApplicant = false }: KanbanBoardProps) {
  const { debouncedName: searchedName } = useSearchApplicant();

  return (
    <S.Container>
      {/* TODO: isSubTab을 가져와서 SubTab을 렌더링 합니다. */}
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
