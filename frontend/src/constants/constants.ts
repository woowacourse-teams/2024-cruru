import { RecruitmentPostTabItems } from '@components/recruitmentPost/RecruitmentPostTab';
import { DashboardTabItems } from '@pages/Dashboard';
import type { Question } from '@customTypes/dashboard';

export const BASE_URL = `${process.env.API_URL}/${process.env.API_VERSION}`;

export const DASHBOARD_TAB_MENUS: Record<string, DashboardTabItems> = {
  applicant: '지원자 관리',
  process: '모집 과정 관리',
  apply: '지원서 관리',
} as const;

export const RECRUITMENT_POST_MENUS: Record<string, RecruitmentPostTabItems> = {
  post: '모집 공고',
  apply: '지원하기',
} as const;

export const DASHBOARD_ID = 1; // TODO: 수정해야합니다.

export const PROCESS = {
  inputField: {
    name: {
      label: '프로세스 이름',
      maxLength: 32,
      placeholder: '32자 이내로 입력해주세요.',
    },
    description: {
      label: '프로세스 설명',
      placeholder: '프로세스에 대한 설명을 입력해주세요.',
    },
  },
} as const;

export const CLUB_ID = 1; // TODO: 수정해야 합니다.

export const QUESTION_TYPE_NAME = {
  SHORT_ANSWER: '단답형',
  LONG_ANSWER: '장문형',
  SINGLE_CHOICE: '객관식(단일 선택)',
  MULTIPLE_CHOICE: '객관식(복수 선택)',
} as const;

export const QUESTION_INPUT_LENGTH = {
  SHORT_ANSWER: 50,
  LONG_ANSWER: 1000,
} as const;

export const DEFAULT_QUESTIONS: Question[] = [
  { type: 'SHORT_ANSWER', question: '이름', choices: [], required: true, id: 0 },
  { type: 'SHORT_ANSWER', question: '이메일', choices: [], required: true, id: 1 },
  { type: 'SHORT_ANSWER', question: '전화번호', choices: [], required: true, id: 2 },
] as const;

export const EDITIONAL_QUESTION_LENGTH = 20;

export const MAX_QUESTION_LENGTH = EDITIONAL_QUESTION_LENGTH + DEFAULT_QUESTIONS.length;

export const APPLY_QUESTION_HEADER = {
  defaultQuestions: {
    title: '지원자 정보',
    description: '아래 항목은 모든 지원자들에게 기본적으로 제출받는 항목입니다.',
  },
  addQuestion: {
    title: '사전질문',
    description: `지원자에게 질문하고 싶은 것이 있다면 입력해 주세요. (최대 ${EDITIONAL_QUESTION_LENGTH}개)`,
  },
};
