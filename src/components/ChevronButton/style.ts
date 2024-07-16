import styled from '@emotion/styled';

const buttonSizes = {
  sm: '24px',
  md: '36px',
  lg: '48px',
};

interface ButtonProps {
  size: 'sm' | 'md' | 'lg';
}

const Button = styled.button<ButtonProps>`
  width: ${(props) => buttonSizes[props.size]};
  height: ${(props) => buttonSizes[props.size]};
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  border: none;
  background-color: transparent;
  cursor: pointer;

  &:hover {
    background-color: gray;
  }

  img {
    width: 60%;
    height: 60%;
  }
`;

const S = {
  Button,
};

export default S;
