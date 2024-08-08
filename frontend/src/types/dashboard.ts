export interface Question {
  type: 'SHORT_ANSWER' | 'LONG_ANSWER' | 'MULTIPLE_CHOICE' | 'DROPDOWN';
  question: string;
  choices: {
    choice: string;
    orderIndex: number;
  }[];
  required: boolean;
}

export interface RecruitmentInfoState {
  startDate: string;
  endDate: string;
  title: string;
  postingContent: string;
}

export type DashboardFormInfo = { questions: Question[] } & RecruitmentInfoState;

export type StepState = 'recruitmentForm' | 'applyForm' | 'finished';

export interface QuestionOption {
  choice: string;
  orderIndex: number;
}

interface Stats {
  accept: number;
  fail: number;
  inProgress: number;
  total: number;
}

interface Dashboard {
  dashboard_id?: string;
  dashboardId?: string;
  title: string;
  stats: Stats;
  postUrl: string;
  startDate: string;
  endDate: string;
}

export interface Club {
  clubName: string;
  dashboards: Dashboard[];
}
