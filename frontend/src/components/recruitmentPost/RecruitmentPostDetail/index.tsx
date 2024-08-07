import Button from '@components/common/Button';
import TextEditor from '@components/common/TextEditor';
import { applyQueries } from '@hooks/apply';
import { useParams } from 'react-router-dom';
import S from './style';
import C from '../style';

interface RecruitmentPostDetailProps {
  moveTab: (e: React.MouseEvent<HTMLButtonElement>) => void;
}

export default function RecruitmentPostDetail({ moveTab }: RecruitmentPostDetailProps) {
  // TODO: smell
  const { postId } = useParams<{ postId: string }>();
  const { data: recruitmentPost, isClosed } = applyQueries.useGetRecruitmentPost({ postId: postId ?? '' });

  return (
    <C.ContentContainer>
      <TextEditor
        theme="bubble"
        value={recruitmentPost?.postingContent || ''}
      />

      <S.ButtonContainer>
        <Button
          name="지원하기"
          onClick={moveTab}
          color="primary"
          size="fillContainer"
          disabled={isClosed}
        >
          지원하기
        </Button>
      </S.ButtonContainer>
    </C.ContentContainer>
  );
}
