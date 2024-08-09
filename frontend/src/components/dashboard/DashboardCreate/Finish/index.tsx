import Button from '@components/common/Button';
import CopyToClipboard from '@components/common/CopyToClipboard';
import { useNavigate, useParams } from 'react-router-dom';
import ChevronButton from '@components/common/ChevronButton';
import S from './style';

interface FinishProps {
  postUrl: string;
  postId: string;
}

export default function Finish({ postUrl, postId }: FinishProps) {
  const navigate = useNavigate();
  const { dashboardId } = useParams() as { dashboardId: string };

  const handleClickButton = () => {
    navigate(`/dashboard/${dashboardId}/${postId}`);
  };

  return (
    <S.Container>
      <S.Icon>🎉</S.Icon>
      <S.Message>공고가 게시됐어요!</S.Message>
      <CopyToClipboard url={`https://${postUrl}`} />
      <Button
        size="sm"
        color="white"
        onClick={handleClickButton}
      >
        <S.ButtonContent>
          공고로 이동
          <ChevronButton
            size="sm"
            direction="right"
          />
        </S.ButtonContent>
      </Button>
    </S.Container>
  );
}
