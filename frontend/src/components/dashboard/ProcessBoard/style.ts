import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
`;

const ColumnWrapper = styled.div`
  width: 100%;
  flex: 1;

  background-color: white;
  display: flex;
  gap: 2rem;

  overflow-x: scroll;
  overflow-y: visible;

  padding-bottom: 1.2rem;
`;

const S = {
  Container,
  ColumnWrapper,
};

export default S;
