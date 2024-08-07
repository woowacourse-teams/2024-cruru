import { ApplyForm, QuestionType, RecruitmentPost } from '@customTypes/apply';
import { ISO8601 } from '@customTypes/common';

interface Choice {
  id: number;
  label: string;
  orderIndex: number;
}

interface Question {
  id: number;
  type: QuestionType;
  label: string;
  description: string;
  orderIndex: number;
  choices: Choice[];
}

export interface ApplyDto {
  title: string;
  postingContent: string;
  startDate: ISO8601;
  endDate: ISO8601;
  questions: Question[];
}

// INFO: 백엔드 API 응답 객체로부터 필요한 데이터를 그루핑하기 위해 DTO를 변환하는 함수를 만들었습니다.
// 2024.08.05 렛서
export function dtoToRecruitmentPost({ title, startDate, endDate, postingContent }: ApplyDto): RecruitmentPost {
  return {
    title,
    startDate,
    endDate,
    postingContent,
  };
}

export function dtoToApplyForm(dto: ApplyDto): ApplyForm {
  return {
    questions: [...dto.questions],
  };
}
