import styled from '@emotion/styled';

export type ButtonSize = 'sm' | 'md' | 'lg';

const Button = styled.button`
  border: none;
  background-color: transparent;

  &:hover {
    cursor: pointer;
  }
`;

const S = {
  Button,
};

export default S;
