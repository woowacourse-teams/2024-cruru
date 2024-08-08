import { QuestionType } from './apply';

export interface QuestionChoice {
  choice: string;
  orderIndex: number;
}

export interface Question {
  type: QuestionType;
  question: string;
  choices: QuestionChoice[];
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

export type QuestionControlActionType = 'moveUp' | 'moveDown' | 'delete';

export interface QuestionOptionValue {
  value: string;
}

interface Stats {
  accept: number;
  fail: number;
  inProgress: number;
  total: number;
}

interface Dashboard {
  dashboardId: string;
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
