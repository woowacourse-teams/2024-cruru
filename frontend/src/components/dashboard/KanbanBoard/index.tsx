import { Process } from '@customTypes/process';
import ProcessColumn from '@components/dashboard/ProcessColumn';
import ApplicantModal from '@components/ApplicantModal';
import S from './style';

interface IKanbanBoardProps {
  processes: Process[];
}

export default function KanbanBoard({ processes }: IKanbanBoardProps) {
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
