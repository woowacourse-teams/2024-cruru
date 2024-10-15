import styled from '@emotion/styled';

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.6rem;
  padding: 2rem 1.6rem;
  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};
`;

const RangeWrapper = styled.div`
  display: flex;
  flex-direction: column;
  width: 24rem;
  gap: 0.8rem;
`;

const RangeLabel = styled.span`
  display: flex;
  justify-content: space-between;
  ${({ theme }) => theme.typography.heading[500]};
`;

const RatingNumbers = styled.div`
  display: flex;
  align-items: center;
  gap: 0.4rem;
  ${({ theme }) => theme.typography.heading[400]};
`;

const OptionsWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
`;

const ButtonWrapper = styled.div`
  display: flex;
  gap: 0.8rem;
`;

const ButtonInner = styled.div`
  ${({ theme }) => theme.typography.heading[300]}
  padding: 0.4rem;
`;

const S = {
  Wrapper,
  RangeWrapper,
  RangeLabel,
  RatingNumbers,
  OptionsWrapper,
  ButtonWrapper,
  ButtonInner,
};

export default S;
