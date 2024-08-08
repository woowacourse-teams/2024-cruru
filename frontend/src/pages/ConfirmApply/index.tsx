import { useLocation, useNavigate, useParams } from 'react-router-dom';
import Button from '@components/common/Button';

import { LuCheckCircle } from 'react-icons/lu';

import S from './style';

export default function ConfirmApply() {
  const location = useLocation();
  const state = location.state as { title: string };

  const { postId } = useParams<{ postId: string }>();

  const navigate = useNavigate();
  const navigateToPost = () => {
    navigate(`/post/${postId}`);
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
          <S.Information>모집 공고에 지원이 완료되었습니다.</S.Information>
        </S.PostTitleContainer>

        <S.ButtonContainer>
          <Button
            color="primary"
            size="fillContainer"
            onClick={navigateToPost}
          >
            공고 화면으로 돌아가기
          </Button>
        </S.ButtonContainer>
      </S.Wrapper>
    </S.PageLayout>
  );
}
