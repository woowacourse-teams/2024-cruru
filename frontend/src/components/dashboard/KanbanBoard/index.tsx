import { Process } from '@customTypes/process';
import ProcessColumn from '@components/dashboard/ProcessColumn';
import ApplicantModal from '@components/appModal';
import S from './style';

interface KanbanBoardProps {
  processes: Process[];
}

export default function KanbanBoard({ processes }: KanbanBoardProps) {
  return (
    <S.Wrapper>
      {processes.map((process) => (
        <ProcessColumn
          key={process.processId}
          process={process}
        />
      ))}

      <ApplicantModal />
    </S.Wrapper>
  );
}
