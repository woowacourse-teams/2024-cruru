import { ApplyForm, QuestionType, RecruitmentPost, Question as CustomQuestion } from '@customTypes/apply';
import { ISO8601 } from '@customTypes/common';

interface ChoiceDto {
  id: number;
  label: string;
  orderIndex: number;
}

interface QuestionDto {
  id: string;
  type: QuestionType;
  label: string;
  description: string;
  orderIndex: number;
  choices: ChoiceDto[];
  required: boolean;
}

export interface ApplyDto {
  title: string;
  postingContent: string;
  startDate: ISO8601;
  endDate: ISO8601;
  questions: QuestionDto[];
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
    questions: dto.questions.map(({ id, type, label, description, orderIndex, choices, required }) => ({
      questionId: id,
      type,
      label,
      description,
      orderIndex,
      choices,
      required,
    })) as CustomQuestion[],
  };
}
