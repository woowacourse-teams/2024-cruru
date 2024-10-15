import { HiOutlineCalendar } from 'react-icons/hi';
import formatDate from '@utils/formatDate';

import RecruitmentStatusFlag from '@components/recruitment/RecruitmentStatusFlag';
import OpenInNewTab from '@components/_common/atoms/OpenInNewTab';
import CopyToClipboard from '@components/_common/atoms/CopyToClipboard';

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
      </S.TitleContainer>

      <S.PostLinkContainer>
        <OpenInNewTab
          url={postUrl}
          title="공고로 이동"
        />
        <CopyToClipboard url={postUrl} />
      </S.PostLinkContainer>
    </S.Wrapper>
  );
}
