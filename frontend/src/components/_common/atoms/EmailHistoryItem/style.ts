import styled from '@emotion/styled';

const Container = styled.div`
  width: 100%;
  padding: 1.6rem 1.2rem;
  border-bottom: 1px solid ${({ theme }) => theme.baseColors.grayscale[400]};
`;

const Header = styled.button`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
`;

const RightSide = styled.div`
  display: flex;
  align-items: center;
  gap: 0.8rem;
`;

const Title = styled.div`
  ${({ theme }) => theme.typography.common.default};
  color: ${({ theme }) => theme.baseColors.grayscale[900]};
`;

const Status = styled.div<{ status: '발송완료' | '발송실패' | '전송중' }>`
  ${({ theme }) => theme.typography.common.small};
  padding: 0.4rem 0.8rem;
  border-radius: 0.4rem;

  background-color: ${({ theme, status }) => {
    if (status === '발송실패') return theme.baseColors.redscale[50];
    if (status === '전송중') return theme.baseColors.grayscale[300];
    return theme.baseColors.grayscale[300];
  }};
  color: ${({ theme, status }) => {
    if (status === '발송실패') return theme.baseColors.redscale[800];
    return theme.baseColors.grayscale[800];
  }};
`;

const Date = styled.span`
  ${({ theme }) => theme.typography.common.small};
  color: ${({ theme }) => theme.baseColors.grayscale[700]};
`;

const ArrowIcon = styled.div<{ isOpen: boolean }>`
  width: 1.6rem;
  height: 1.6rem;

  svg {
    width: 100%;
    height: 100%;
    transform: ${({ isOpen }) => (isOpen ? 'rotate(180deg)' : 'rotate(0deg)')};
    transition: transform 0.2s ease-in-out;
  }
`;

const Content = styled.div<{ isOpen: boolean }>`
  margin-top: 1.6rem;
  padding: 1.2rem 0.8rem;

  ${({ theme }) => theme.typography.common.default};
  color: ${({ theme }) => theme.baseColors.grayscale[900]};
  background-color: ${({ theme }) => theme.baseColors.grayscale[100]};

  white-space: pre-wrap;
  display: ${({ isOpen }) => (isOpen ? 'block' : 'none')};
`;

const S = {
  Container,
  Header,
  RightSide,
  Title,
  ArrowIcon,
  Content,
  Date,
  Status,
};

export default S;
