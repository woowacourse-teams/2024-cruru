import styled from '@emotion/styled';

interface ContainerProps {
  size: 'sm' | 'md';
  isOpen: boolean;
  popOverPosition?: string;
  right?: number;
}

const Container = styled.div<ContainerProps>`
  width: ${({ size }) => (size === 'sm' ? '120px' : '240px')};

  position: absolute;
  inset: ${({ popOverPosition }) => popOverPosition ?? 'inherit'};

  border-radius: ${({ isOpen }) => (isOpen ? '8px 8px 0px 0px' : '8px')};
  box-shadow: 0px 4px 4px ${({ theme }) => theme.baseColors.grayscale[400]};

  display: ${({ isOpen }) => (isOpen ? 'block' : 'none')};

  z-index: 1;
`;

const ListWrapper = styled.div`
  padding: 0 0.8rem 0.8rem;
`;

const List = styled.div<{ size: 'sm' | 'md' }>`
  width: 100%;
  padding: ${({ size }) => (size === 'md' ? '16px 0px' : '8px 0px')};
  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};

  border: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
  border-radius: 8px;

  box-sizing: border-box;
  box-shadow: 0px 4px 4px ${({ theme }) => theme.baseColors.grayscale[400]};
`;

const S = {
  Container,
  ListWrapper,
  List,
};

export default S;
