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
      {processes.map((process) => (
        <ProcessColumn
          key={process.processId}
          process={process}
          showRejectedApplicant={showRejectedApplicant}
        />
      ))}

      <ApplicantModal />
    </S.Wrapper>
  );
}
