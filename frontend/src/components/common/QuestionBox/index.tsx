import S from './style';

interface TextQuestionBoxProps {
  header: string;
  type: 'text';
  content?: string;
}

interface FileQuestionBoxProps {
  header: string;
  type: 'file';
  fileName?: string;
  onFileDownload?: () => void;
}

type QuestionBoxProps = TextQuestionBoxProps | FileQuestionBoxProps;

const renderContent = (props: QuestionBoxProps) => {
  if (props.type === 'text') {
    return <S.Content>{props.content}</S.Content>;
  }

  if (props.type === 'file') {
    return (
      <S.Content>
        <S.FileRow>
          <S.FileName>{props.fileName}</S.FileName>
          <S.DownloadIcon onClick={props.onFileDownload}>&#x2193;</S.DownloadIcon>
        </S.FileRow>
      </S.Content>
    );
  }

  return null;
};

export default function QuestionBox(props: QuestionBoxProps) {
  const { header } = props;

  return (
    <S.Container>
      <S.Header>{header}</S.Header>
      {renderContent(props)}
    </S.Container>
  );
}
