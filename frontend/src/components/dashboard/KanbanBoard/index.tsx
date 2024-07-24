import { Process } from '@customTypes/process';
import ProcessColumn from '@components/dashboard/ProcessColumn';
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
    </S.Wrapper>
  );
}
