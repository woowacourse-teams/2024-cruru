import { RecruitmentStatusObject } from '@utils/compareTime';

export interface SidebarStyle {
  isSidebarOpen: boolean;
  onClickSidebarToggle: () => void;
}

export interface RecruitmentPost {
  text: string;
  isSelected: boolean;
  applyFormId: string;
  dashboardId: number;
  status: RecruitmentStatusObject;
}
