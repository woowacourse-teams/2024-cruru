import { useLocation, useNavigate, useParams } from 'react-router-dom';
import Button from '@components/_common/atoms/Button';

import { LuCheckCircle } from 'react-icons/lu';

import { routes } from '@router/path';
import S from './style';

export default function ConfirmApply() {
  const location = useLocation();
  const state = location.state as { title: string };

  const { applyFormId } = useParams() as { applyFormId: string };

  const navigate = useNavigate();
  const navigateToPost = () => {
    navigate(routes.post({ applyFormId }));
  };

  if (!state) {
    navigateToPost();
  }

  return (
    <S.PageLayout>
      <S.Wrapper>
        <LuCheckCircle />

        <S.PostTitleContainer>
          <S.Title>{`"${state.title}"`}</S.Title>
          <S.Information>
            제출이 완료되었습니다.
            <br />
            좋은 결과 얻으시길 기원합니다.
          </S.Information>
        </S.PostTitleContainer>

        <S.ButtonContainer>
          <Button
            color="primary"
            size="fillContainer"
            onClick={navigateToPost}
          >
            모집 공고로 돌아가기
          </Button>
        </S.ButtonContainer>
      </S.Wrapper>
    </S.PageLayout>
  );
}
