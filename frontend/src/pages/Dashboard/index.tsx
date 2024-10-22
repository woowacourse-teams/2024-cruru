/* eslint-disable no-trailing-spaces */
import { useParams } from 'react-router-dom';

import Tab from '@components/_common/molecules/Tab';
import ApplyManagement from '@components/applyManagement';
import DashboardHeader from '@components/dashboard/DashboardHeader';
import ProcessBoard from '@components/dashboard/ProcessBoard';
import PostManageBoard from '@components/postManagement/PostManageBoard';
import ProcessManageBoard from '@components/processManagement/ProcessManageBoard';

import useTab from '@components/_common/molecules/Tab/useTab';
import { useSearchApplicant } from '@components/dashboard/useSearchApplicant';
import useProcess from '@hooks/useProcess';

import { DASHBOARD_TAB_MENUS } from '@constants/constants';
import { FloatingEmailFormProvider } from '@contexts/FloatingEmailFormContext';
import { MultiApplicantContextProvider } from '@contexts/MultiApplicantContext';
import { SpecificApplicantIdProvider } from '@contexts/SpecificApplicnatIdContext';
import { SpecificProcessIdProvider } from '@contexts/SpecificProcessIdContext';

import S from './style';

export type DashboardTabItems = '지원자 관리' | '모집 과정 관리' | '불합격자 관리' | '공고 관리' | '지원서 관리';

export default function Dashboard() {
  const { currentMenu, moveTab } = useTab<DashboardTabItems>({ defaultValue: '지원자 관리' });

  const { debouncedName } = useSearchApplicant();
  // TODO: [10.15-lesser] sub tab이 구현되면 아래 코드를 사용합니다.
  // const { debouncedName, name, updateName } = useSearchApplicant();
  // const { processes, title, postUrl, startDate, endDate, sortOption, updateSortOption } = useProcess({
  //   dashboardId,
  //   applyFormId,
  // });

  const { dashboardId, applyFormId } = useParams() as { dashboardId: string; applyFormId: string };
  const { processes, title, postUrl, startDate, endDate } = useProcess({
    dashboardId,
    applyFormId,
  });

  return (
    <S.AppContainer>
      <DashboardHeader
        title={title}
        postUrl={postUrl}
        startDate={startDate}
        endDate={endDate}
      />

      <Tab>
        {Object.values(DASHBOARD_TAB_MENUS).map((label) => (
          <Tab.TabItem
            key={label}
            label={label}
            name={label}
            isActive={currentMenu === label}
            handleClickTabItem={moveTab}
          />
        ))}
      </Tab>

      {/* TODO: [08.21-lurgi] 현재 모달이 여러개를 컨트롤 할 수 없는 관계로 새로 렌더링 합니다.
      추후에 Modal에 id값을 부여하여 여러개의 모달을 컨트롤 할 수 있게 변경해야합니다.
      파일 맨 첫줄 주석도 삭제해야합니다. */}
      <FloatingEmailFormProvider>
        <MultiApplicantContextProvider>
          <Tab.TabPanel isVisible={currentMenu === '지원자 관리'}>
            {/* [10.15-lesser] sub tab이 구현되면 아래 코드를 사용합니다. */}
            {/* <InputField
              type="search"
              placeholder="지원자 이름 검색"
              value={name}
              onChange={(e) => updateName(e.target.value)}
            /> */}
            {/* <ApplicantSortDropdown
              sortOption={sortOption}
              updateSortOption={updateSortOption}
            /> */}
            <S.DashboardContainer>
              <SpecificApplicantIdProvider>
                <SpecificProcessIdProvider>
                  <ProcessBoard
                    isSubTab
                    processes={processes}
                    searchedName={debouncedName}
                  />
                </SpecificProcessIdProvider>
              </SpecificApplicantIdProvider>
            </S.DashboardContainer>
          </Tab.TabPanel>

          <Tab.TabPanel isVisible={currentMenu === '불합격자 관리'}>
            <S.DashboardContainer>
              <SpecificApplicantIdProvider>
                <SpecificProcessIdProvider>
                  <ProcessBoard
                    processes={processes}
                    showRejectedApplicant
                  />
                </SpecificProcessIdProvider>
              </SpecificApplicantIdProvider>
            </S.DashboardContainer>
          </Tab.TabPanel>
        </MultiApplicantContextProvider>
      </FloatingEmailFormProvider>

      <Tab.TabPanel isVisible={currentMenu === '모집 과정 관리'}>
        <S.DashboardContainer>
          <ProcessManageBoard
            dashboardId={dashboardId}
            applyFormId={applyFormId}
            processes={processes}
          />
        </S.DashboardContainer>
      </Tab.TabPanel>

      <Tab.TabPanel isVisible={currentMenu === '공고 관리'}>
        <S.DashboardContainer>
          <PostManageBoard applyFormId={applyFormId} />
        </S.DashboardContainer>
      </Tab.TabPanel>

      <Tab.TabPanel isVisible={currentMenu === '지원서 관리'}>
        <S.DashboardContainer>
          <ApplyManagement />
        </S.DashboardContainer>
      </Tab.TabPanel>
    </S.AppContainer>
  );
}
