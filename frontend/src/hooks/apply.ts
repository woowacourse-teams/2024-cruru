import applyApis from '@api/apply';
import { useQuery } from '@tanstack/react-query';
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
      data: data?.applyForm,
      ...restQueryObj,
    };
  },
};
