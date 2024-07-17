export interface ApplicantCardInfo {
  applicant_id: number;
  applicant_name: string;
  created_at: string;
}

export interface Process {
  processId: number;
  orderIndex: number;
  name: string;
  description: string;
  applicants: ApplicantCardInfo[];
}
