export interface SpecificApplicant {
  applicantId: number;
  createdAt: string;
  name: string;
  email: string;
  phone: string;
  processName: string;
}

export interface EvaluationResult {
  evaluationId: number;
  evaluatorName: string;
  score: string;
  content: string;
  createdAt: string;
}
