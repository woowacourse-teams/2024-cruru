import S from './style';
import ProcessColumn from '../ProcessColumn';
import { Process } from '@/types/process';

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
    </S.Wrapper>
  );
}
