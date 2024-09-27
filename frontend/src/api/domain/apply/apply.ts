/* eslint-disable @typescript-eslint/no-explicit-any */
import { ApplyForm, ApplyRequestBody, RecruitmentPost } from '@customTypes/apply';
import { dtoToApplyForm, dtoToRecruitmentPost } from './applyDto';
import { APPLY } from '../../endPoint';
import APIClient from '../../APIClient';

const apiClient = new APIClient(APPLY);

const applyApis = {
  get: async ({
    applyFormId,
  }: {
    applyFormId: string;
  }): Promise<{ recruitmentPost: RecruitmentPost; applyForm: ApplyForm }> => {
    const data = await apiClient.get<any>({
      path: `/${applyFormId}`,
    });

    const recruitmentPost = dtoToRecruitmentPost(data);
    const applyForm = dtoToApplyForm(data);

    return { recruitmentPost, applyForm };
  },

  apply: async ({ applyFormId, body }: { applyFormId: string; body: ApplyRequestBody }) =>
    apiClient.post({
      path: `/${applyFormId}/submit`,
      body,
    }),

  modify: async ({ applyFormId, body }: { applyFormId: string; body: RecruitmentPost }) =>
    apiClient.patch({
      path: `/${applyFormId}`,
      body,
    }),
};

export default applyApis;
