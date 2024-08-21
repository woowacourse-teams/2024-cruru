/* eslint-disable default-case */
/* eslint-disable consistent-return */
import styled from '@emotion/styled';

interface ToastContainerProps {
  type: 'default' | 'success' | 'error' | 'primary';
}

const ToastContainer = styled.div<ToastContainerProps>`
  min-width: 20rem;
  max-width: max(50vw, 32rem);
  display: flex;
  align-items: center;
  justify-content: center;

  width: fit-content;
  height: 4.8rem;

  padding: 0 1.2rem;

  border-radius: 0.8rem;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.3);

  background-color: ${({ type, theme }) => {
    switch (type) {
      case 'default':
        return theme.baseColors.grayscale[50];
      case 'success':
        return theme.colors.feedback.success;
      case 'error':
        return theme.colors.feedback.error;
      case 'primary':
        return theme.colors.brand.primary;
    }
  }};
  color: ${({ type, theme }) =>
    type === 'default' ? theme.baseColors.grayscale[800] : theme.baseColors.grayscale[50]};
  ${({ theme }) => theme.typography.common.block}
`;

const Message = styled.div`
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;

  max-width: 100%;
`;

const S = {
  ToastContainer,
  Message,
};

export default S;
