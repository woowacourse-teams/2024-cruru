import styled from '@emotion/styled';
import { hideScrollBar } from '@styles/utils';

const Container = styled.div`
  display: flex;
  flex-direction: column;
  padding: 3.8rem 2.4rem;
  gap: 2.4rem;

  height: 100%;
`;

const Title = styled.h1`
  ${({ theme }) => theme.typography.heading[700]}
  padding-bottom: 2.4rem;
  border-bottom: 1px solid ${({ theme }) => theme.baseColors.grayscale[300]};
`;

const CardGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fill, 37.8rem);
  gap: 2.4rem;

  overflow-y: scroll;
  ${hideScrollBar}
`;

const AddCard = styled.div`
  display: flex;
  height: 20rem;

  flex-direction: column;
  align-items: center;
  justify-content: center;

  border: 0.2rem dashed ${({ theme }) => theme.baseColors.grayscale[600]};
  border-radius: 0.5rem;
  padding: 2rem;

  cursor: pointer;
  text-align: center;

  &:hover {
    background-color: ${({ theme }) => theme.baseColors.grayscale[200]};
  }

  div {
    font-size: 6rem;
    color: ${({ theme }) => theme.colors.brand.primary};
  }
  span {
    ${({ theme }) => theme.typography.common.large}
    color:  ${({ theme }) => theme.baseColors.grayscale[800]};
  }
`;

const S = {
  Container,
  Title,
  CardGrid,
  AddCard,
};

export default S;
