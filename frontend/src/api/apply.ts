import { ApplyForm, ApplyRequestBody, RecruitmentPost } from '@customTypes/apply';
import ApiError from './ApiError';
import { dtoToApplyForm, dtoToRecruitmentPost } from './applyDto';
import { APPLY } from './endPoint';

const applyApis = {
  get: async ({ postId }: { postId: string }): Promise<{ recruitmentPost: RecruitmentPost; applyForm: ApplyForm }> => {
    const response = await fetch(`${APPLY}/${postId}`, {
      headers: {
        Accept: 'application/json',
      },
    });

    if (!response.ok) {
      throw new ApiError({
        method: 'GET',
        statusCode: response.status,
        message: '공고 정보를 불러오지 못했습니다.',
      });
    }

    const data = await response.json();
    const recruitmentPost = dtoToRecruitmentPost(data);
    const applyForm = dtoToApplyForm(data);

    return { recruitmentPost, applyForm };
  },

  apply: async ({ postId, body }: { postId: string; body: ApplyRequestBody }) => {
    const response = await fetch(`${APPLY}/${postId}/submit`, {
      method: 'POST',
      headers: {
        'Content-type': 'application/json',
      },
      body: JSON.stringify(body),
    });

    if (!response.ok) {
      throw new ApiError({
        method: 'POST',
        statusCode: response.status,
        message: '지원서를 제출하지 못했습니다.',
      });
    }

    return response;
  },
};

export default applyApis;
