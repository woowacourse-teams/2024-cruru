import styled from '@emotion/styled';

const Container = styled.div`
  height: 9rem;
  padding: 0 1.6rem;
`;

const Header = styled.div`
  display: flex;
  align-items: center;
  margin-bottom: 1rem;
`;

const Tab = styled.div<{ active: boolean }>`
  ${({ theme }) => theme.typography.heading[600]}
  color: ${({ active, theme }) => (active ? theme.colors.text.default : theme.baseColors.grayscale[500])};
  margin-right: 2.4rem;
  cursor: pointer;
  position: relative;

  &:last-child {
    margin-right: 0;
  }

  &:not(:last-child)::after {
    content: '•';
    width: 1rem;
    aspect-ratio: 1/1;
    right: -1.7rem;
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    color: ${({ theme }) => theme.baseColors.grayscale[500]};
  }
`;

const Content = styled.div`
  ${({ theme }) => theme.typography.common.paragraph}
`;

const S = {
  Container,
  Header,
  Tab,
  Content,
};

export default S;
