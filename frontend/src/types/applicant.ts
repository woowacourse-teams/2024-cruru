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
  evaluatorName: string;
  score: string;
  content: string;
  createdDate: string;
}
