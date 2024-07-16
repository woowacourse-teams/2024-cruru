import S from './style';
import ProcessColumn from '../ProcessColumn';
import { Process } from '@/types/process';

interface KanbanBoardProps {
  processes: Process[];
}

export default function KanbanBoard({ processes }: KanbanBoardProps) {
  const processNameList = processes.map((process) => process.name);

  return (
    <S.Wrapper>
      {processes.map((process) => (
        <ProcessColumn
          key={process.processId}
          process={process}
          processNameList={processNameList}
        />
      ))}
    </S.Wrapper>
  );
}
