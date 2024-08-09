import styled from '@emotion/styled';

const CardContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  min-width: 15rem;
  padding: 1rem 1.6rem;
  border-radius: 0.8rem;

  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};
  border: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};

  transition: all 0.2s;

  &:hover {
    scale: 1.01;
    transform: translateY(-0.1rem);
    box-shadow: 0 0.2rem 0.6rem rgba(0, 0, 0, 0.1);
    border: 1px solid ${({ theme }) => theme.baseColors.grayscale[500]};
    cursor: pointer;
  }
`;

const CardDetail = styled.div`
  display: flex;
  flex-direction: column;
`;

const CardHeader = styled.h3`
  ${({ theme }) => theme.typography.common.block};
  color: ${({ theme }) => theme.colors.text.default};
  margin-bottom: 0.4rem;
`;

const CardDate = styled.span`
  ${({ theme }) => theme.typography.common.small};
  color: ${({ theme }) => theme.baseColors.grayscale[800]};
`;

const OptionButtonWrapper = styled.div`
  position: relative;
`;

const S = {
  CardContainer,
  CardDetail,
  CardHeader,
  CardDate,
  OptionButtonWrapper,
};

export default S;
