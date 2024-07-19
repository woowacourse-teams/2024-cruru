import styled from '@emotion/styled';

const Container = styled.div<{ size: 'sm' | 'md'; isOpen: boolean }>`
  width: ${({ size }) => (size === 'sm' ? '150px' : '300px')};
  position: relative;

  border-radius: ${({ isOpen }) => (isOpen ? '8px 8px 0px 0px' : '8px')};
  box-shadow: 0px 4px 4px ${({ theme }) => theme.baseColors.grayscale[400]};
`;

const Header = styled.div<{ isOpen: boolean }>`
  display: flex;
  justify-content: space-between;
  padding: 20px 24px;

  border: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
  border-radius: ${({ isOpen }) => (isOpen ? '8px 8px 0px 0px' : '8px')};
  border-bottom: ${({ isOpen, theme }) => (isOpen ? 'none' : `1px sold ${theme.baseColors.grayscale[400]}`)};

  ${({ theme }) => theme.typography.heading[500]}
  margin-bottom: 0;
  cursor: pointer;

  ${({ isOpen, theme }) =>
    isOpen &&
    ` ::after {
        content: '';
        position: absolute;
        bottom: 0;
        left: 24px; 
        right: 24px;
        height: 1px;
        background-color: ${theme.baseColors.grayscale[400]};
      }
    `}
`;

const List = styled.div`
  position: absolute;
  width: 100%;
  padding: 8px;
  background-color: ${({ theme }) => theme.baseColors.grayscale[50]};

  border: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
  border-radius: 0px 0px 8px 8px;
  border-top: none;

  box-sizing: border-box;
  box-shadow: 0px 4px 4px ${({ theme }) => theme.baseColors.grayscale[400]};

  z-index: 1;
`;

const S = {
  Container,
  Header,
  List,
};

export default S;
