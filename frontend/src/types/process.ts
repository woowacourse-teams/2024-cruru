export interface ApplicantCardInfo {
  applicantId: number;
  applicantName: string;
  createdAt: string;
  isRejected: boolean;
  evaluationCount: number;
}

export interface Process {
  processId: number;
  orderIndex: number;
  name: string;
  description: string;
  applicants: ApplicantCardInfo[];
}

export interface ProcessResponse {
  postUrl: string;
  title: string;
  processes: Process[];
}
