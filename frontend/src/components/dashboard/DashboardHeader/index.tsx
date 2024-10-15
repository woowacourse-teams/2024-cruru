import formatDate from '@utils/formatDate';
import { HiOutlineCalendar } from 'react-icons/hi';

import OpenInNewTab from '@components/_common/atoms/OpenInNewTab';
import RecruitmentStatusFlag from '@components/recruitment/RecruitmentStatusFlag';

import S from './style';

interface DashboardHeaderProps {
  title: string;
  postUrl: string;
  startDate: string;
  endDate: string;
}

export default function DashboardHeader({ title, postUrl, startDate, endDate }: DashboardHeaderProps) {
  return (
    <S.Wrapper>
      <S.TitleContainer>
        <S.Title>
          {title}
          <OpenInNewTab
            url={postUrl}
            title="공고로 이동"
          />
        </S.Title>
        <S.RecruitmentStatusContainer>
          <S.RecruitmentPeriod>
            <HiOutlineCalendar style={{ strokeWidth: '0.25rem' }} />
            {`${formatDate(startDate)} ~ ${formatDate(endDate)}`}
          </S.RecruitmentPeriod>
          <RecruitmentStatusFlag
            startDate={startDate}
            endDate={endDate}
          />
        </S.RecruitmentStatusContainer>
      </S.TitleContainer>
    </S.Wrapper>
  );
}
