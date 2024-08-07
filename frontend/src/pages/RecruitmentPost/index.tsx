import { applyQueries } from '@hooks/apply';
import { ISOToLocaleString } from '@utils/formatDate';
import { HiOutlineClock } from 'react-icons/hi2';
import { useParams } from 'react-router-dom';
import RecruitmentPostTab from '@components/recruitmentPost/RecruitmentPostTab';
import PostIdNotFound from './PostIdNotFound';

import S from './style';

export default function RecruitmentPost() {
  const { postId } = useParams<{ postId: string }>();

  if (!postId) {
    return <PostIdNotFound />;
  }

  const { data: recruitmentPost } = applyQueries.useGetRecruitmentPost({ postId });
  const recruitmentPeriod = {
    startDate: ISOToLocaleString({ date: recruitmentPost?.startDate }),
    endDate: ISOToLocaleString({ date: recruitmentPost?.endDate }),
  };

  return (
    <S.PageLayout>
      <S.Wrapper>
        <S.Header>
          <S.Title>{recruitmentPost?.title}</S.Title>
          <S.PeriodContainer>
            <HiOutlineClock />
            <S.Period>{Object.values(recruitmentPeriod).join(' ~ ')}</S.Period>
          </S.PeriodContainer>
        </S.Header>

        <RecruitmentPostTab />
      </S.Wrapper>
    </S.PageLayout>
  );
}
