import Tab from '@components/_common/atoms/Tab';
import useTab from '@components/_common/atoms/Tab/useTab';

import { RECRUITMENT_POST_MENUS } from '@constants/constants';
import { applyQueries } from '@hooks/apply';
import { useParams } from 'react-router-dom';
import { Question } from '@customTypes/apply';
import RecruitmentPostDetail from '../RecruitmentPostDetail';
import ApplyForm from '../ApplyForm';

export type RecruitmentPostTabItems = '모집 공고' | '지원하기';

export default function RecruitmentPostTab() {
  const { currentMenu, moveTab } = useTab<RecruitmentPostTabItems>({ defaultValue: '모집 공고' });

  const { applyFormId } = useParams<{ applyFormId: string }>() as { applyFormId: string };
  const { data: questions } = applyQueries.useGetApplyForm({ applyFormId: applyFormId ?? '' });
  const { data: recruitmentPost, isClosed } = applyQueries.useGetRecruitmentPost({ applyFormId: applyFormId ?? '' });

  return (
    <>
      <Tab>
        {Object.values(RECRUITMENT_POST_MENUS).map((label) => (
          <Tab.TabItem
            key={label}
            label={label}
            name={label}
            isActive={currentMenu === label}
            handleClickTabItem={moveTab}
            disabled={label === '지원하기' && isClosed}
          />
        ))}
      </Tab>

      <Tab.TabPanel isVisible={currentMenu === '모집 공고'}>
        <RecruitmentPostDetail
          recruitmentPost={recruitmentPost}
          isClosed={isClosed}
          moveTab={moveTab}
        />
      </Tab.TabPanel>
      <Tab.TabPanel isVisible={currentMenu === '지원하기'}>
        <ApplyForm
          isClosed={isClosed}
          questions={questions ?? ([] as Question[])}
        />
      </Tab.TabPanel>
    </>
  );
}
