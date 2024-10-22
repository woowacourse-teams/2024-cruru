import { ApplicantDetail, SpecificApplicant } from '@customTypes/applicant';
import APIClient from '../APIClient';
import { APPLICANTS } from '../endPoint';

const apiClient = new APIClient(APPLICANTS);

const moveApplicant = async ({ processId, applicants }: { processId: number; applicants: number[] }) =>
  apiClient.put<{ status: number }>({
    path: `/move-process/${processId}`,
    body: {
      applicantIds: applicants,
    },
  });

const getSpecificApplicant = async ({ applicantId }: { applicantId: number }) =>
  apiClient.get<SpecificApplicant>({
    path: `/${applicantId}`,
  });

const rejectApplicant = async ({ applicantId }: { applicantId: number }) =>
  apiClient.patch<{ status: number }>({
    path: `/${applicantId}/reject`,
  });

const unrejectApplicant = async ({ applicantId }: { applicantId: number }) =>
  apiClient.patch<{ status: number }>({
    path: `/${applicantId}/unreject`,
  });

const getDetailApplicant = async ({ applicantId }: { applicantId: number }) =>
  apiClient.get<ApplicantDetail>({
    path: `/${applicantId}/detail`,
  });

const rejectApplicants = async ({ applicantIds }: { applicantIds: number[] }) =>
  apiClient.patch<{ status: number }>({
    path: '/reject',
    body: {
      applicantIds,
    },
  });

const unrejectApplicants = async ({ applicantIds }: { applicantIds: number[] }) =>
  apiClient.patch<{ status: number }>({
    path: '/unreject',
    body: {
      applicantIds,
    },
  });

const applicantApis = {
  move: moveApplicant,
  get: getSpecificApplicant,
  reject: rejectApplicant,
  unreject: unrejectApplicant,
  getDetail: getDetailApplicant,
  rejectApplicants,
  unrejectApplicants,
};

export default applicantApis;
