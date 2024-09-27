import Button from '@components/_common/atoms/Button';
import TextEditor from '@components/_common/atoms/TextEditor';
import { RecruitmentPost } from '@customTypes/apply';
import C from '../style';

interface RecruitmentPostDetailProps {
  recruitmentPost?: RecruitmentPost;
  isClosed: boolean;
  moveTab: (e: React.MouseEvent<HTMLButtonElement>) => void;
}

export default function RecruitmentPostDetail({ recruitmentPost, isClosed, moveTab }: RecruitmentPostDetailProps) {
  return (
    <C.ContentContainer>
      <TextEditor
        theme="bubble"
        value={recruitmentPost?.postingContent || ''}
      />

      <C.ButtonContainer>
        <Button
          name="지원하기"
          onClick={moveTab}
          color="primary"
          size="fillContainer"
          disabled={isClosed}
        >
          지원하기
        </Button>
      </C.ButtonContainer>
    </C.ContentContainer>
  );
}
