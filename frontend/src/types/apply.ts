import { ISO8601 } from './common';

export interface RecruitmentPost {
  title: string;
  startDate: ISO8601;
  endDate: ISO8601;
  postingContent: string;
}

export type QuestionType = 'SHORT_ANSWER' | 'LONG_ANSWER' | 'MULTIPLE_CHOICE' | 'SINGLE_CHOICE';

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
