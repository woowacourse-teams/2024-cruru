import { keyframes } from '@emotion/react';
import styled from '@emotion/styled';

const fadeIn = keyframes`
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
`;

const Container = styled.div`
  width: 100vw;
  height: 100vh;

  display: flex;
  justify-content: center;
  align-items: center;
`;
const Logo = styled.img`
  height: 6rem;
  animation: ${fadeIn} 1.5s ease-out;
`;

const S = {
  Container,
  Logo,
};

export default S;
