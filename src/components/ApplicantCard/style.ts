import styled from '@emotion/styled';
import EllipsisIcon from '../../assets/images/ellipsis.svg';

const CardContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 1.6rem;
  border-radius: 0.8rem;
  box-shadow: 0 0.4rem 0.8rem rgba(0, 0, 0, 0.1);
  width: 30rem;
  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};
`;

const CardDetail = styled.div`
  display: flex;
  flex-direction: column;
`;

const CardHeader = styled.h3`
  ${({ theme }) => theme.typography.heading[400]};
  color: ${({ theme }) => theme.baseColors.grayscale[900]};
  margin-bottom: 0.4rem;
`;

const CardDate = styled.span`
  ${({ theme }) => theme.typography.heading[300]};
  color: ${({ theme }) => theme.baseColors.grayscale[800]};
`;

const OptionButton = styled.button`
  width: 3.6rem;
  height: 3.6rem;
  display: flex;
  align-items: center;
  justify-content: center;
  background: url('${EllipsisIcon}') center no-repeat;
  background-size: 2.4rem;
  border-radius: 0.8rem;
  transition: background-color 0.3s ease;

  &:hover {
    background-color: ${({ theme }) => theme.colors.hover.bg};
  }
`;

const S = {
  CardContainer,
  CardDetail,
  CardHeader,
  CardDate,
  OptionButton,
};

export default S;
