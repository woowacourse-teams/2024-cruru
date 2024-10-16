import ApplicantModal from '@components/ApplicantModal';
import { Process } from '@customTypes/process';

import { useMultiApplicant } from '@contexts/MultiApplicantContext';
import ProcessColumn from '../ProcessColumn';
import SideFloatingMessageForm from '../SideFloatingMessageForm';
import S from './style';
import MultiSelectToggle from '../MultiSelectToggle';

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
  const { isMultiType, toggleIsMultiType, resetApplicants } = useMultiApplicant();

  const handleToggleMultiType = () => {
    if (isMultiType) resetApplicants();
    toggleIsMultiType();
  };

  return (
    <S.Container>
      {/* TODO: isSubTab을 가져와서 SubTab을 렌더링 합니다. */}
      <MultiSelectToggle
        onToggle={handleToggleMultiType}
        isSelectMode={isMultiType}
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
