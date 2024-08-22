import { RecruitmentInfoState } from '@customTypes/dashboard';
import { applyQueries } from '@hooks/apply';
import { useEffect, useState } from 'react';

interface usePostManagementProps {
  postId: string;
}

const INITIAL_POST_INFO: RecruitmentInfoState = {
  title: '',
  startDate: '',
  endDate: '',
  postingContent: '',
};

export default function usePostManagement({ postId }: usePostManagementProps) {
  const { data: postInfo, isLoading } = applyQueries.useGetRecruitmentPost({ postId });
  const [postState, setPostState] = useState<RecruitmentInfoState>(INITIAL_POST_INFO);

  useEffect(() => {
    if (!isLoading && postInfo && Object.keys(postInfo).length > 0) {
      setPostState(postInfo);
    }
  }, [isLoading, postInfo]);

  return {
    isLoading,
    postState,

    setPostState,
  };
}
