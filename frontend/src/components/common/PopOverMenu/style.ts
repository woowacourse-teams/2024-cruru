import styled from '@emotion/styled';

interface ContainerProps {
  size: 'sm' | 'md';
  isOpen: boolean;
  popOverPosition?: string;
}

const Container = styled.div<ContainerProps>`
  min-width: ${({ size }) => (size === 'sm' ? '90px' : '240px')};
  position: absolute;
  inset: ${({ popOverPosition }) => popOverPosition ?? 'inherit'};

  border-radius: ${({ isOpen }) => (isOpen ? '8px 8px 0px 0px' : '8px')};
  box-shadow: 0px 4px 4px ${({ theme }) => theme.colors.text.block};

  display: ${({ isOpen }) => (isOpen ? 'block' : 'none')};

  z-index: 1;
`;

const List = styled.div<{ size: 'sm' | 'md' }>`
  width: 100%;
  padding: ${({ size }) => (size === 'md' ? '16px' : '8px')};
  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};

  border: 1px solid ${({ theme }) => theme.colors.text.block};
  border-radius: 8px;

  box-sizing: border-box;
  box-shadow: 0px 4px 4px ${({ theme }) => theme.colors.text.block};
`;

const S = {
  Container,
  List,
};

export default S;
