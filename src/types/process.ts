export interface ApplicantCardInfo {
  id: number;
  name: string;
  createdAt: string;
}

export interface Process {
  processId: number;
  orderIndex: number;
  name: string;
  description: string;
  applicants: ApplicantCardInfo[];
}
