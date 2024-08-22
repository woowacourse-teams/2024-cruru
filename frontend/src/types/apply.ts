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
  questionId: string;
  type: QuestionType;
  label: string;
  description: string;
  choices: Choice[];
  required: boolean;
}

export interface ApplyForm {
  questions: Question[];
}

// RequestBody
export interface ApplicantData {
  name: string;
  email: string;
  phone: string;
}

export interface AnswerData {
  questionId: string;
  replies: string[];
}

export interface ApplyRequestBody {
  applicant: ApplicantData;
  answers: AnswerData[];
  personalDataCollection: boolean;
}
