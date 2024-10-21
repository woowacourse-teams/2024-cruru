import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;

  overflow-x: scroll;
  overflow-y: visible;

  height: 100%;
`;

const ColumnWrapper = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  gap: 2rem;

  padding-bottom: 1.2rem;
`;

const S = {
  Container,
  ColumnWrapper,
};

export default S;
