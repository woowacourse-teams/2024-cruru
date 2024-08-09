import { RecruitmentPostTabItems } from '@components/recruitmentPost/RecruitmentPostTab';
import { DashboardTabItems } from '@pages/Dashboard';

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
