import { DashboardMenus } from '@components/dashboard/DashboardTab';

export const NAV_BAR_MENU: Record<DashboardMenus, string> = {
  applicant: '지원자 관리',
  process: '프로세스 관리',
} as const;

export const DASHBOARD_ID = 1; // TODO: 수정해야합니다.
