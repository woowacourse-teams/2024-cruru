import styled from '@emotion/styled';

const Wrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2.4rem;
`;

const FieldWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  gap: 1rem;
`;

const ButtonWrapper = styled.div`
  height: 4.6rem;
  margin-top: 2.8rem;
  color: ${({ theme }) => theme.colors.brand.primary};
`;

const ButtonInner = styled.div`
  width: 5rem;
  height: 2rem;

  display: flex;
  align-items: center;
  justify-content: center;
`;

const ConfirmButtonWrapper = styled.div`
  height: 4.6rem;
`;

const TimerInputWrapper = styled.div`
  position: relative;
  width: 100%;
`;

const Timer = styled.div`
  position: absolute;
  right: 1.4rem;
  top: 1.4rem;

  ${({ theme }) => theme.typography.common.block}
  color : ${({ theme }) => theme.baseColors.grayscale[700]}
`;

const S = {
  Wrapper,
  FieldWrapper,
  ButtonWrapper,
  ButtonInner,
  ConfirmButtonWrapper,
  TimerInputWrapper,
  Timer,
};

export default S;
