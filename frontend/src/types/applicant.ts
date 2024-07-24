export interface SpecificApplicant {
  applicantId: number;
  createdAt: string;
  name: string;
  email: string;
  phone: string;
  processName: string;
}

export type EvaluationScore = 1 | 2 | 3 | 4 | 5;
