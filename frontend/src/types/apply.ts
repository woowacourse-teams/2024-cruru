import { ISO8601 } from './common';

export interface RecruitmentPost {
  title: string;
  startDate: ISO8601;
  endDate: ISO8601;
  postingContent: string;
}

export type QuestionType = 'SHORT_ANSWER' | 'LONG_ANSWER' | 'MULTIPLE_CHOICE' | 'DROPDOWN' | 'CHECK_BOX';

export interface Choice {
  id: number;
  label: string;
}

export interface Question {
  id: number;
  type: QuestionType;
  label: string;
  description: string;
  choices: Choice[];
}

export interface ApplyForm {
  questions: Question[];
}
