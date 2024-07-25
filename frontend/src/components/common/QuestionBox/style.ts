import styled from '@emotion/styled';

const Container = styled.div`
  overflow: hidden;

  border: 0.1rem solid ${({ theme }) => theme.baseColors.grayscale[500]};
  border-left: none;
  border-right: none;

  width: 100%;
  min-width: 20rem;
`;

const Header = styled.div`
  ${({ theme }) => theme.typography.heading[400]}

  padding: 0.8rem 1.6rem;
  background-color: ${({ theme }) => theme.baseColors.grayscale[300]};
  font-size: 1.4rem;
  display: flex;
  align-items: center;
`;

const Content = styled.div`
  ${({ theme }) => theme.typography.common.paragraph}
  padding: 1.6rem;
`;

const FileRow = styled.div`
  display: flex;
  align-items: center;
`;

const FileName = styled.span`
  margin-right: 0.8rem;
`;

const DownloadIcon = styled.span`
  font-size: 1.6rem;
  cursor: pointer;
  color: ${({ theme }) => theme.baseColors.grayscale[800]};
`;

const S = {
  Container,
  Header,
  Content,
  FileRow,
  FileName,
  DownloadIcon,
};

export default S;
