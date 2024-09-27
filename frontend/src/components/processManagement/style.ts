import { Theme, css } from '@emotion/react';
import styled from '@emotion/styled';

/**
 * 사용하는 곳에서 position을 relative로 설정해주세요.
 * @param theme 색상 지정을 위해 theme을 받아옵니다.
 * @returns
 */
export const connectionLine = (theme: Theme) => css`
  &:not(:last-child)::after {
    content: '';
    position: absolute;
    top: 0.8rem;
    right: calc(-1.8rem); // INFO: -(width + border)
    width: 1.8rem;
    height: 0.2rem;
    background-color: ${theme.baseColors.grayscale[400]};
  }
`;

const ProcessForm = styled.form`
  position: relative;

  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 2.4rem;

  padding: 2.4rem 1.6rem;

  border-radius: 0.8rem;
  border: 0.1rem solid ${({ theme }) => theme.baseColors.grayscale[300]};
  background-color: ${({ theme }) => theme.baseColors.grayscale[100]};

  height: fit-content;
  box-sizing: border-box;
  ${({ theme }) => connectionLine(theme)}
`;

const ButtonWrapper = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.8rem;

  button {
    padding: 0.8rem;
  }
`;

const C = {
  ProcessForm,
  ButtonWrapper,
};

export default C;
