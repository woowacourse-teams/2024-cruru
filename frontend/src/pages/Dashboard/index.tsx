/* eslint-disable no-trailing-spaces */
import { useOutletContext, useParams } from 'react-router-dom';

import type { DashboardTabItems } from '@pages/DashboardLayout';

import Tab from '@components/_common/molecules/Tab';
import ApplyManagement from '@components/applyManagement';
import DashboardHeader from '@components/dashboard/DashboardHeader';
import ProcessBoard from '@components/dashboard/ProcessBoard';
import PostManageBoard from '@components/postManagement/PostManageBoard';
import ProcessManageBoard from '@components/processManagement/ProcessManageBoard';

import useProcess from '@hooks/useProcess';

import { FloatingEmailFormProvider } from '@contexts/FloatingEmailFormContext';
import { MultiApplicantContextProvider } from '@contexts/MultiApplicantContext';
import { SpecificApplicantIdProvider } from '@contexts/SpecificApplicnatIdContext';
import { SpecificProcessIdProvider } from '@contexts/SpecificProcessIdContext';

import S from './style';

export default function Dashboard() {
  const { currentMenu } = useOutletContext<{ currentMenu: DashboardTabItems }>();
  const { dashboardId, applyFormId } = useParams() as { dashboardId: string; applyFormId: string };
  const { processes, title, postUrl, startDate, endDate } = useProcess({
    dashboardId,
    applyFormId,
  });

  return (
    <S.AppContainer>
      <S.DashboardHeaderWrapper>
        <DashboardHeader
          title={title}
          postUrl={postUrl}
          startDate={startDate}
          endDate={endDate}
        />
      </S.DashboardHeaderWrapper>

      {/* TODO: [08.21-lurgi] 현재 모달이 여러개를 컨트롤 할 수 없는 관계로 새로 렌더링 합니다.
      추후에 Modal에 id값을 부여하여 여러개의 모달을 컨트롤 할 수 있게 변경해야합니다.
      파일 맨 첫줄 주석도 삭제해야합니다. */}
      <Tab.TabPanel isVisible={currentMenu === '지원자 관리'}>
        <S.DashboardContainer>
          <SpecificApplicantIdProvider>
            <SpecificProcessIdProvider>
              <MultiApplicantContextProvider>
                <FloatingEmailFormProvider>
                  <ProcessBoard
                    isSubTab
                    processes={processes}
                  />
                </FloatingEmailFormProvider>
              </MultiApplicantContextProvider>
            </SpecificProcessIdProvider>
          </SpecificApplicantIdProvider>
        </S.DashboardContainer>
      </Tab.TabPanel>

      <Tab.TabPanel isVisible={currentMenu === '불합격자 관리'}>
        <S.DashboardContainer>
          <SpecificApplicantIdProvider>
            <SpecificProcessIdProvider>
              <MultiApplicantContextProvider>
                <FloatingEmailFormProvider>
                  <ProcessBoard
                    processes={processes}
                    showRejectedApplicant
                  />
                </FloatingEmailFormProvider>
              </MultiApplicantContextProvider>
            </SpecificProcessIdProvider>
          </SpecificApplicantIdProvider>
        </S.DashboardContainer>
      </Tab.TabPanel>

      <Tab.TabPanel isVisible={currentMenu === '모집 과정 관리'}>
        <S.DashboardContainer>
          <ProcessManageBoard
            dashboardId={dashboardId}
            applyFormId={applyFormId}
            processes={processes}
          />
        </S.DashboardContainer>
      </Tab.TabPanel>

      <Tab.TabPanel isVisible={currentMenu === '공고 편집'}>
        <S.DashboardContainer>
          <PostManageBoard applyFormId={applyFormId} />
        </S.DashboardContainer>
      </Tab.TabPanel>

      <Tab.TabPanel isVisible={currentMenu === '지원서 편집'}>
        <S.DashboardContainer>
          <ApplyManagement />
        </S.DashboardContainer>
      </Tab.TabPanel>
    </S.AppContainer>
  );
}
