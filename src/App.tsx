import styled from '@emotion/styled';
import plusIcon from '@assets/images/plus.svg';
import { useQuery } from '@tanstack/react-query';
import IconButton from '@components/IconButton';
import KanbanBoard from '@components/KanbanBoard';
import { getProcesses } from '@/api/process';
import { Process } from './types/process';

const AppContainer = styled.div`
  width: 100vw;
  height: 100vh;
`;

export default function App() {
  const ID = 1; // TODO: 수정해야합니다.
  const { data, error, isLoading } = useQuery<{ processes: Process[] }>({
    queryKey: ['dashboard', ID],
    queryFn: () => getProcesses({ id: ID }),
  });

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (error || !data) {
    return <div>Error: {error?.message}</div>;
  }

  const { processes } = data;

  return (
    <AppContainer>
      <KanbanBoard processes={processes} />
      <IconButton
        type="button"
        onClick={() => console.log('clicked')}
        size="sm"
        outline
      >
        <img
          src={plusIcon}
          alt="플러스 아이콘"
        />
      </IconButton>
    </AppContainer>
  );
}
