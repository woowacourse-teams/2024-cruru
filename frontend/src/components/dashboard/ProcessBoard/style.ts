import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
  gap: 1.6rem;
`;

const ColumnWrapper = styled.div`
  width: 100%;
  flex: 1;

  display: flex;

  overflow-x: scroll;
  overflow-y: visible;

  padding-bottom: 1.2rem;
  gap: 2.4rem;
`;

const S = {
  Container,
  ColumnWrapper,
};

export default S;
