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
  text-overflow: ellipsis;
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

const Empty = styled.div`
  width: 1.6rem;
`;

const S = {
  Container,
  Input,
  DeleteBtn,
  Empty,
};

export default S;
