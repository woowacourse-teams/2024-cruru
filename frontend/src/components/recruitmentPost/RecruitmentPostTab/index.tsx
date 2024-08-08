import Tab from '@components/common/Tab';
import useTab from '@components/common/Tab/useTab';

import { RECRUITMENT_POST_MENUS } from '@constants/constants';
import { applyQueries } from '@hooks/apply';
import { useParams } from 'react-router-dom';
import { Question } from '@customTypes/apply';
import RecruitmentPostDetail from '../RecruitmentPostDetail';
import ApplyForm from '../ApplyForm';

export type RecruitmentPostTabItems = '모집 공고' | '지원하기';

export default function RecruitmentPostTab() {
  const { currentMenu, moveTab } = useTab<RecruitmentPostTabItems>({ defaultValue: '모집 공고' });

  const { postId } = useParams<{ postId: string }>() as { postId: string };
  const { data: questions } = applyQueries.useGetApplyForm({ postId: postId ?? '' });

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
          />
        ))}
      </Tab>

      <Tab.TabPanel isVisible={currentMenu === '모집 공고'}>
        <RecruitmentPostDetail moveTab={moveTab} />
      </Tab.TabPanel>
      <Tab.TabPanel isVisible={currentMenu === '지원하기'}>
        <ApplyForm questions={questions ?? ([] as Question[])} />
      </Tab.TabPanel>
    </>
  );
}
