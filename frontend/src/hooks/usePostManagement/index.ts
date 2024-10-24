import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useEffect, useState } from 'react';

import applyApis from '@api/domain/apply/apply';
import { useToast } from '@contexts/ToastContext';
import { RecruitmentPost } from '@customTypes/apply';
import { RecruitmentInfoState } from '@customTypes/dashboard';
import { applyQueries } from '@hooks/apply';
import QUERY_KEYS from '@hooks/queryKeys';
import useClubId from '@hooks/service/useClubId';

interface usePostManagementProps {
  applyFormId: string;
}

const INITIAL_POST_INFO: RecruitmentInfoState = {
  title: '',
  startDate: '',
  endDate: '',
  postingContent: '',
};

export default function usePostManagement({ applyFormId }: usePostManagementProps) {
  const toast = useToast();
  const queryClient = useQueryClient();
  const { clubId } = useClubId();

  const { data: postInfo, isLoading } = applyQueries.useGetRecruitmentPost({ applyFormId });
  const [postState, setPostState] = useState<RecruitmentInfoState>(INITIAL_POST_INFO);

  useEffect(() => {
    if (!isLoading && postInfo && Object.keys(postInfo).length > 0) {
      setPostState(postInfo);
    }
  }, [isLoading, postInfo]);

  const modifyPostMutator = useMutation({
    mutationFn: () => applyApis.modify({ applyFormId, body: postState as RecruitmentPost }),
    onSuccess: async () => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.DASHBOARD, clubId] });
      toast.success('공고의 내용 수정에 성공했습니다.');
    },
    onError: () => {
      toast.error('공고의 내용 수정에 실패했습니다.');
    },
  });

  return {
    isLoading,
    postState,
    setPostState,
    modifyPostMutator,
  };
}
