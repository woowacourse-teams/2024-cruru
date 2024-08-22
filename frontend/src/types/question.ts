import { QUESTION_TYPE_NAME } from '@constants/constants';

type QuestionType = keyof typeof QUESTION_TYPE_NAME;

interface QuestionChoice {
  choice: string;
  orderIndex: number;
}

export interface ModifyQuestionData {
  orderIndex: number;
  type: QuestionType;
  question: string;
  choices: QuestionChoice[];
  required: boolean;
}
