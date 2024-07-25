import { EvaluationScore } from '@customTypes/applicant';
import theme from '@styles/theme';

export const NAV_BAR_MENU = {
  applicant: '지원자 관리',
  process: '프로세스 관리',
} as const;

export const DASHBOARD_ID = 1; // TODO: 수정해야합니다.

export const EVALUATION_SCORE: Record<EvaluationScore, { color: string; bgColor: string; description: string }> = {
  1: {
    color: theme.baseColors.redscale[500],
    bgColor: theme.baseColors.redscale[50],
    description: '별로예요',
  },
  2: {
    color: theme.baseColors.redscale[400],
    bgColor: theme.baseColors.redscale[50],
    description: '잘 모르겠어요',
  },
  3: {
    color: theme.baseColors.grayscale[700],
    bgColor: theme.baseColors.grayscale[300],
    description: '그저 그래요',
  },
  4: {
    color: theme.baseColors.bluescale[400],
    bgColor: theme.baseColors.bluescale[50],
    description: '좋아요',
  },
  5: {
    color: theme.baseColors.bluescale[500],
    bgColor: theme.baseColors.bluescale[50],
    description: '아주 좋아요',
  },
} as const;
