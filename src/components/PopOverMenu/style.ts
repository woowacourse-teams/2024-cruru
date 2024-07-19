import styled from '@emotion/styled';

const Container = styled.div<{ size: 'sm' | 'md'; isOpen: boolean }>`
  width: ${({ size }) => (size === 'sm' ? '150px' : '300px')};
  position: absolute;

  border-radius: ${({ isOpen }) => (isOpen ? '8px 8px 0px 0px' : '8px')};
  box-shadow: 0px 4px 4px ${({ theme }) => theme.baseColors.grayscale[400]};

  display: ${({ isOpen }) => (isOpen ? 'block' : 'none')};
`;

const List = styled.div`
  position: relative;
  top: 10px;
  width: 100%;
  padding: 8px;
  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};

  border: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
  border-radius: 8px;

  box-sizing: border-box;
  box-shadow: 0px 4px 4px ${({ theme }) => theme.baseColors.grayscale[400]};

  z-index: 1;
`;

const S = {
  Container,
  List,
};

export default S;
