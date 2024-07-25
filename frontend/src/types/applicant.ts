export interface SpecificApplicant {
  applicantId: number;
  createdAt: string;
  name: string;
  email: string;
  phone: string;
  processName: string;
}

export interface EvaluationResult {
  score: string;
  content: string;
}
