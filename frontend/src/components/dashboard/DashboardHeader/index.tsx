import { HiOutlineCalendar } from 'react-icons/hi';
import RecruitmentStatusFlag from '@components/recruitment/RecruitmentStatusFlag';
import formatDate from '@utils/formatDate';

import S from './style';

interface DashboardHeaderProps {
  title: string;
  startDate: string;
  endDate: string;
}

export default function DashboardHeader({ title, startDate, endDate }: DashboardHeaderProps) {
  return (
    <S.Wrapper>
      <S.Title>{title}</S.Title>
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
    </S.Wrapper>
  );
}
