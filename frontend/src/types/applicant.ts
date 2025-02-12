interface ApplicantBaseInfo {
  id: number;
  name: string;
  email: string;
  phone: string;
  createdAt: string;
  isRejected: boolean;
}

interface ApplicantProcessInfo {
  id: number;
  name: string;
}

export interface SpecificApplicant {
  applicant: ApplicantBaseInfo;
  process: ApplicantProcessInfo;
}

export interface EmailHistory {
  id: number;
  email: string;
  isSent: boolean;
  status?: '전송 중' | '전송 완료' | '전송 실패';
}

interface DetailInfo {
  orderIndex: number;
  question: string;
  answer: string;
}

export interface ApplicantDetail {
  details: DetailInfo[];
}

export interface EvaluationResult {
  evaluationId: number;
  evaluator: string;
  score: string;
  content: string;
  createdDate: string;
}
