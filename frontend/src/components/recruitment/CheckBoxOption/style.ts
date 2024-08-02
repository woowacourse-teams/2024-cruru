import styled from '@emotion/styled';

const Container = styled.div`
  display: flex;
  align-items: center;
  padding: 0.5rem 0;
  display: flex;
  gap: 0.4rem;
`;

const Input = styled.input`
  ${({ theme }) => theme.typography.common.large}
  border: none;

  padding: 0.4rem;
  flex: 1;

  font-size: 1rem;
  color: ${({ theme }) => theme.colors.text.default};

  border-bottom: 0.1rem solid transparent;

  &:focus {
    border-bottom: 0.1rem solid ${({ theme }) => theme.baseColors.grayscale[400]};
    outline: none;
  }
`;

const DeleteBtn = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;

  font-size: 1.6rem;
  color: ${({ theme }) => theme.baseColors.grayscale[600]};
  border: none;
  cursor: pointer;
`;

const S = {
  Container,
  Input,
  DeleteBtn,
};

export default S;
