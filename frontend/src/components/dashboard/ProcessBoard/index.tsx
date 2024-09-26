import { Process } from '@customTypes/process';
import ApplicantModal from '@components/ApplicantModal';
import ProcessColumn from '../ProcessColumn';
import S from './style';

interface KanbanBoardProps {
  processes: Process[];
  showRejectedApplicant?: boolean;
}

export default function ProcessBoard({ processes, showRejectedApplicant = false }: KanbanBoardProps) {
  return (
    <S.Wrapper>
      {processes.map((process, index) => (
        <ProcessColumn
          key={process.processId}
          process={process}
          showRejectedApplicant={showRejectedApplicant}
          isPassedColumn={!showRejectedApplicant && index === processes.length - 1}
        />
      ))}

      <ApplicantModal />
    </S.Wrapper>
  );
}
