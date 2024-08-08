import applyApis from '@api/apply';
import { useMutation, useQuery } from '@tanstack/react-query';
import { ApplyRequestBody } from '@customTypes/apply';
import { useNavigate } from 'react-router-dom';
import QUERY_KEYS from './queryKeys';

const useGetRecruitmentInfo = ({ postId }: { postId: string }) => {
  const queryObj = useQuery({
    queryKey: [QUERY_KEYS.RECRUITMENT_INFO, postId],
    queryFn: () => applyApis.get({ postId }),
    staleTime: 1000 * 60 * 10, // 10 minutes
  });

  return queryObj;
};

export const applyQueries = {
  useGetRecruitmentPost: ({ postId }: { postId: string }) => {
    const { data, ...restQueryObj } = useGetRecruitmentInfo({ postId });

    const isClosed = data ? data.recruitmentPost.endDate < new Date().toISOString() : true;

    return {
      isClosed,
      data: data?.recruitmentPost,
      ...restQueryObj,
    };
  },

  useGetApplyForm: ({ postId }: { postId: string }) => {
    const { data, ...restQueryObj } = useGetRecruitmentInfo({ postId });

    return {
      data: data?.applyForm.questions,
      ...restQueryObj,
    };
  },
};

export const applyMutations = {
  useApply: (postId: string) => {
    const navigate = useNavigate();

    return useMutation({
      mutationFn: (params: { body: ApplyRequestBody }) => applyApis.apply({ ...params, postId }),
      onError: (error) => {
        window.alert(error.message);
      },
      onSuccess: () => {
        navigate(`/post/${postId}/confirm`);
      },
    });
  },
};
