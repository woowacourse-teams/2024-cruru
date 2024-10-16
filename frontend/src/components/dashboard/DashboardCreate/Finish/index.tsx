import Button from '@components/_common/atoms/Button';
import { routes } from '@router/path';
import { useNavigate } from 'react-router-dom';
import { DOMAIN_URL } from '@constants/constants';
import { HiChevronRight } from 'react-icons/hi';
import SharePost from '../SharePost';
import S from './style';

interface FinishProps {
  dashboardId: string;
  applyFormId: string;
}

export default function Finish({ dashboardId, applyFormId }: FinishProps) {
  const navigate = useNavigate();

  const handleClickButton = () => {
    navigate(routes.dashboard.post({ dashboardId, applyFormId }));
  };

  return (
    <S.Container>
      <S.Icon>🎉</S.Icon>
      <S.Message>공고가 게시됐어요!</S.Message>
      <SharePost url={`${DOMAIN_URL}${routes.post({ applyFormId })}`} />
      <Button
        size="sm"
        color="white"
        onClick={handleClickButton}
      >
        <S.ButtonContent>
          대시보드로 이동
          <HiChevronRight size={24} />
        </S.ButtonContent>
      </Button>
    </S.Container>
  );
}
