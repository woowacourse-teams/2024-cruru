import Tab from '@components/common/Tab';
import useTab from '@components/common/Tab/useTab';
import { RECRUITMENT_POST_MENUS } from '@constants/constants';
import RecruitmentPostDetail from '../RecruitmentPostDetail';
import ApplyForm from '../ApplyForm';

export type RecruitmentPostTabItems = '모집 공고' | '지원하기';

export default function RecruitmentPostTab() {
  const { currentMenu, moveTab } = useTab<RecruitmentPostTabItems>({ defaultValue: '모집 공고' });

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
        <ApplyForm />
      </Tab.TabPanel>
    </>
  );
}
