import { styled } from 'styled-system/jsx';
import cat from '@/assets/images/cat.svg';

const StyledDiv = styled.div`
  background-color: red;
  width: 100px;
`;

export default function App() {
  return (
    <StyledDiv className="app">
      <img src={cat} />
      렛서 판다 붐은 온다
    </StyledDiv>
  );
}
