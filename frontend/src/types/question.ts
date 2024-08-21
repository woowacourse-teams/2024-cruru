import { QUESTION_TYPE_NAME } from '@constants/constants';

type QuestionType = keyof typeof QUESTION_TYPE_NAME;

interface QuestionChoice {
  label: string;
  orderIndex: number;
}

export interface ModifyQuestionData {
  orderIndex: number;
  type: QuestionType;
  label: string;
  choices: QuestionChoice[];
  required: boolean;
}
