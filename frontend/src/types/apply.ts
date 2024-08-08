import { QUESTION_TYPE_NAME } from '@constants/constants';
import { ISO8601 } from './common';

export interface RecruitmentPost {
  title: string;
  startDate: ISO8601;
  endDate: ISO8601;
  postingContent: string;
}

export type QuestionType = keyof typeof QUESTION_TYPE_NAME;

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
