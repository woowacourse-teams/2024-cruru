import styled from '@emotion/styled';
import plusIcon from '@assets/images/plus.svg';
import KanbanBoard from './components/KanbanBoard';
import IconButton from './components/IconButton';

import processMockData from './mocks/processMockData';

const AppContainer = styled.div`
  width: 100vw;
  height: 100vh;
`;

export default function App() {
  return (
    <AppContainer>
      <KanbanBoard processes={processMockData} />
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
