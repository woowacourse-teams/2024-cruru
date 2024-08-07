import styled from '@emotion/styled';

const ModalOverlay = styled.dialog`
  max-width: 100%;
  max-height: 100%;
  min-width: 300px;

  justify-content: center;
  align-items: center;
  z-index: 1000;
  padding: 0;

  border: 1px solid ${({ theme }) => theme.colors.text.block};
  border-radius: 8px;

  width: fit-content;
  height: fit-content;

  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  overflow: hidden;

  &::backdrop {
    background-color: rgba(0, 0, 0, 0.2);
  }
`;

const S = {
  ModalOverlay,
};

export default S;
