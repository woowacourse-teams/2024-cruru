import styled from '@emotion/styled';

const Wrapper = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;

  height: 4.8rem;
`;

const FunctionsContainer = styled.section`
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 1.2rem;

  width: fit-content;
`;

const FilterWrapper = styled.div`
  position: relative;
`;

const FilterButtonContent = styled.span`
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 0.4rem;
`;

const FilterContainer = styled.div`
  position: absolute;
  transform: translateY(0.8rem);

  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};
  border: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
  border-radius: 0.8rem;
  box-shadow: ${({ theme }) => `0 0.2rem 0.6rem ${theme.baseColors.grayscale[400]}`};
  padding: 0.2rem;
  z-index: 10;
`;

const S = {
  Wrapper,
  FunctionsContainer,
  FilterButtonContent,
  FilterWrapper,
  FilterContainer,
};

export default S;
