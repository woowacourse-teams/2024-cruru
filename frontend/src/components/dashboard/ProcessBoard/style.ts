import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
`;

const ColumnWrapper = styled.div`
  width: 100%;
  height: 100%;
  background-color: white;
  display: flex;
  gap: 2.4rem;

  padding-bottom: 1.2rem;

  overflow-x: scroll;
  overflow-y: visible;
`;

const S = {
  Container,
  ColumnWrapper,
};

export default S;
