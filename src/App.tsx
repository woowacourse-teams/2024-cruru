import plusIcon from '@assets/images/plus.svg';
import ApplicantCard from './components/ApplicantCard';
import Button from './components/Button';

import styled from '@emotion/styled';

const AppContainer = styled.div`
  width: 100vw;
  height: 100vh;
`;

export default function App() {
  return (
    <AppContainer>
      <ApplicantCard />
      <Button
        type="button"
        onClick={() => console.log('clicked')}
        icon={
          <img
            src={plusIcon}
            alt="플러스 아이콘"
          />
        }
        size="sm"
        outline={true}
      />
    </AppContainer>
  );
}
