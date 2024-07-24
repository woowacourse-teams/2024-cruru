import { EvaluationScore } from '@customTypes/applicant';

export const NAV_BAR_MENU = {
  applicant: '지원자 관리',
  process: '프로세스 관리',
  settings: '설정',
} as const;

export const DASHBOARD_ID = 1; // TODO: 수정해야합니다.

export const EVALUATION_SCORE: Record<EvaluationScore, string> = {
  1: '별로예요',
  2: '잘 모르겠어요',
  3: '그저 그래요',
  4: '좋아요',
  5: '아주 좋아요',
} as const;
