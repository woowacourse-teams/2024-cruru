/* eslint-disable @typescript-eslint/no-explicit-any */
import { ApplyForm, ApplyRequestBody, RecruitmentPost } from '@customTypes/apply';
import { dtoToApplyForm, dtoToRecruitmentPost } from './applyDto';
import { APPLY } from '../../endPoint';
import APIClient from '../../APIClient';

const apiClient = new APIClient(APPLY);

const applyApis = {
  get: async ({ postId }: { postId: string }): Promise<{ recruitmentPost: RecruitmentPost; applyForm: ApplyForm }> => {
    const data = await apiClient.get<any>({
      path: `/${postId}`,
    });

    const recruitmentPost = dtoToRecruitmentPost(data);
    const applyForm = dtoToApplyForm(data);

    return { recruitmentPost, applyForm };
  },

  apply: async ({ postId, body }: { postId: string; body: ApplyRequestBody }) =>
    apiClient.post({
      path: `/${postId}/submit`,
      body,
    }),
};

export default applyApis;
