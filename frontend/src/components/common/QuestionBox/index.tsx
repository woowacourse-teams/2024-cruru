import S from './style';

interface QuestionBoxProps {
  header: string;
  type: 'text' | 'file';
  content: string;
  fileName?: string;
  onFileDownload?: () => void;
}

export default function QuestionBox({ header, type, content, fileName, onFileDownload }: QuestionBoxProps) {
  return (
    <S.Container>
      <S.Header>{header}</S.Header>
      {type === 'text' ? (
        <S.Content>{content}</S.Content>
      ) : (
        <S.Content>
          <S.FileRow>
            <S.FileName>{fileName}</S.FileName>
            <S.DownloadIcon onClick={onFileDownload}>&#x2193;</S.DownloadIcon>
          </S.FileRow>
        </S.Content>
      )}
    </S.Container>
  );
}
