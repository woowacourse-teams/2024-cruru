export interface ApplicantCardInfo {
  applicantId: number;
  applicantName: string;
  createdAt: string;
  isRejected: boolean;
  evaluationCount: number;
  averageScore: number;
}

export interface Process {
  processId: number;
  orderIndex: number;
  name: string;
  description: string;
  applicants: ApplicantCardInfo[];
}

export interface ProcessResponse {
  applyFormId: string;
  title: string;
  processes: Process[];
  startDate: string;
  endDate: string;
}

export type EvaluationStatus = 'ALL' | 'NOT_EVALUATION' | 'EVALUATED';

type FilterParams = {
  minScore?: string;
  maxScore?: string;
  evaluationStatus?: EvaluationStatus;
};
export type ProcessFilterOptions = FilterParams;

type SortOption = 'ASC' | 'DESC';
type SortParams = {
  sortByCreatedAt?: SortOption;
  sortByScore?: SortOption;
};
export type ProcessSortOption = keyof SortParams;

export type ProcessQueryParams = {
  dashboardId: string;
} & FilterParams &
  SortParams;
