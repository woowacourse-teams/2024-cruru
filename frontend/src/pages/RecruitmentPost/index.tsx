import RecruitmentPostTab from '@components/recruitmentPost/RecruitmentPostTab';
import { applyQueries } from '@hooks/apply';
import { ISOtoLocaleString } from '@utils/formatDate';
import { HiOutlineClock } from 'react-icons/hi2';
import { useParams } from 'react-router-dom';

import S from './style';

export default function RecruitmentPost() {
  const { applyFormId } = useParams<{ applyFormId: string }>();

  const { data: recruitmentPost } = applyQueries.useGetRecruitmentPost({ applyFormId: applyFormId ?? '' });
  const recruitmentPeriod = {
    startDate: ISOtoLocaleString({ date: recruitmentPost?.startDate }),
    endDate: ISOtoLocaleString({ date: recruitmentPost?.endDate }),
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
