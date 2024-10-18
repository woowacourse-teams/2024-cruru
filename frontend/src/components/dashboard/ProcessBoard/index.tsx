import type { Process } from '@customTypes/process';

import ApplicantModal from '@components/ApplicantModal';
import ProcessColumn from '../ProcessColumn';
import SideFloatingMessageForm from '../SideFloatingMessageForm';
import S from './style';

interface ProcessBoardProps {
  processes: Process[];
  // eslint-disable-next-line react/no-unused-prop-types
  isSubTab?: boolean;
  showRejectedApplicant?: boolean;
  searchedName?: string;
}

export default function ProcessBoard({
  processes,
  showRejectedApplicant = false,
  searchedName = '',
}: ProcessBoardProps) {
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
