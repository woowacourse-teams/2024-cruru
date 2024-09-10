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
  id?: number;
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
  choice: string;
}

interface Stats {
  accept: number;
  fail: number;
  inProgress: number;
  total: number;
}

interface Dashboard {
  dashboardId: string;
  applyFormId: string;
  title: string;
  stats: Stats;
  startDate: string;
  endDate: string;
}

export interface Club {
  clubName: string;
  dashboards: Dashboard[];
}
