export interface SpecificApplicant {
  applicantId: number;
  createdAt: string;
  name: string;
  email: string;
  phone: string;
  processName: string;
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
  createdAt: string;
}
