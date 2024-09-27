import styled from '@emotion/styled';

const ButtonGroup = styled.div`
  width: fit-content;
  height: fit-content;
  display: flex;
  flex-direction: column;
  overflow: hidden;

  button:first-of-type {
    border-top-right-radius: 0.8rem;
  }

  button:last-child {
    border-bottom-right-radius: 0.8rem;
    margin-bottom: 0px;
  }
`;

const Button = styled.button<{ isDeleteButton?: boolean }>`
  width: 3.6rem;
  height: 3.6rem;
  display: flex;
  align-items: center;
  justify-content: center;

  background: ${({ theme }) => theme.baseColors.grayscale[50]};
  border: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
  border-left: none;
  margin-bottom: -1px;

  color: ${({ theme, isDeleteButton }) =>
    isDeleteButton ? theme.colors.feedback.error : theme.baseColors.grayscale[900]};
  transition: background 0.2s ease-in-out;

  :hover {
    background: ${({ theme }) => theme.colors.hover.bg};
  }

  svg {
    width: 1.6rem;
    height: 1.6rem;
  }
`;

const S = {
  ButtonGroup,
  Button,
};

export default S;
