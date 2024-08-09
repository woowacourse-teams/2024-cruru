import styled from '@emotion/styled';

const Wrapper = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 1.2rem;
`;

const ChoiceContainer = styled.div`
  width: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 1rem;

  ${({ theme }) => theme.typography.common.default}
`;

const ChoiceInputGroup = styled.div`
  width: calc(100% - 3rem);
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 1rem;

  svg {
    color: ${({ theme }) => theme.baseColors.grayscale[700]};
  }
`;

const ChoiceInput = styled.input`
  width: 100%;
  height: 2.8rem;
  border-bottom: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
  transition: border 0.2s ease-in-out;
  outline: none;
  color: ${({ theme }) => theme.colors.text.default};

  :hover {
    border-color: ${({ theme }) => theme.baseColors.grayscale[700]};
  }

  :focus,
  :active {
    border-bottom: 1px solid ${({ theme }) => theme.baseColors.purplescale[500]};
  }

  :disabled {
    border: none;
  }

  ::placeholder {
    color: ${({ theme }) => theme.baseColors.grayscale[600]};
  }
`;

const DeleteButton = styled.button`
  width: 2rem;
  height: 2rem;
  display: flex;
  align-items: center;
  justify-content: center;

  border-radius: 0.8rem;
  transition: background 0.2s ease-in-out;

  :hover {
    background: ${({ theme }) => theme.colors.hover.bg};
  }
`;

const S = {
  Wrapper,
  ChoiceContainer,
  ChoiceInputGroup,
  ChoiceInput,
  DeleteButton,
};

export default S;
