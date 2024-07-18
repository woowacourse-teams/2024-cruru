import styled from '@emotion/styled';
import plusIcon from '@assets/images/plus.svg';

import IconButton from '@components/IconButton';
import KanbanBoard from '@components/KanbanBoard';
import useProcess from './hooks/useProcess';

import processMockData from './mocks/processMockData';

const AppContainer = styled.div`
  width: 100vw;
  height: 100vh;
`;

export default function App() {
  const { processes, isLoading, error } = useProcess();

  if (isLoading) {
    //TODO: Loading 핸들링
    return <div>Loading...</div>;
  }

  if (error) {
    //TODO: Error 핸들링
    return <div>Error</div>;
  }

  return (
    <AppContainer>
      <KanbanBoard processes={processes} />
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
