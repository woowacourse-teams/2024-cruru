import plusIcon from '@assets/images/plus.svg';
import ApplicantCard from './components/ApplicantCard';

import styled from '@emotion/styled';
import IconButton from './components/IconButton';

const AppContainer = styled.div`
  width: 100vw;
  height: 100vh;
`;

export default function App() {
  return (
    <AppContainer>
      <ApplicantCard />
      <IconButton
        type="button"
        onClick={() => console.log('clicked')}
        size="sm"
        outline={true}
      >
        <img
          src={plusIcon}
          alt="플러스 아이콘"
        />
      </IconButton>
    </AppContainer>
  );
}
