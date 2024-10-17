import styled from '@emotion/styled';

const Wrapper = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;

  height: 4.8rem;
  margin: -0.4rem 0 1.6rem;
`;

const FunctionsContainer = styled.section`
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 1.6rem;

  width: fit-content;
`;

const S = {
  Wrapper,
  FunctionsContainer,
};

export default S;
