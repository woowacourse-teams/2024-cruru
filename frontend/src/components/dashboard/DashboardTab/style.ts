import styled from '@emotion/styled';

const Container = styled.nav`
  height: 4rem;
  display: flex;
  align-items: center;
  padding: 3rem 1rem;
  border-bottom: 0.1rem solid ${({ theme }) => theme.baseColors.grayscale[400]};
`;

const Tabs = styled.ul`
  width: 100%;
  list-style-type: none;

  display: flex;
  flex-direction: row;
  gap: 2.8rem;
`;

const Tab = styled.li`
  position: relative;
  padding: 0.4rem 0;

  &:not(:last-child)::after {
    content: '•';
    width: 2.8rem;
    text-align: center;

    position: absolute;
    transform: translateY(75%);
    color: ${({ theme }) => theme.baseColors.grayscale[500]};
  }
`;

const TabButton = styled.button<{ isCurrent: boolean }>`
  ${({ theme }) => theme.typography.heading[500]};
  color: ${({ isCurrent, theme }) => (isCurrent ? theme.baseColors.grayscale[950] : theme.baseColors.grayscale[500])};

  cursor: pointer;
  padding: 0.5rem 0;
  transition: color 0.3s ease;

  &:hover {
    color: ${({ theme }) => theme.baseColors.grayscale[950]};
  }
`;

const S = {
  Container,
  Tabs,
  Tab,
  TabButton,
};

export default S;
