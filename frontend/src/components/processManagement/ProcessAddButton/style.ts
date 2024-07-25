import styled from '@emotion/styled';
import { connectionLine } from '../style';

const Container = styled.div`
  display: flex;
  align-items: flex-start;

  position: relative;
  ${({ theme }) => connectionLine(theme)}
`;

const S = {
  Container,
};

export default S;
