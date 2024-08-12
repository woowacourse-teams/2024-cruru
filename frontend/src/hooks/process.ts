import processApis from '@api/process';
import { useMutation, useQueryClient } from '@tanstack/react-query';

// 제안: 이런식으로 리팩토링 하는 건 어떨지?
export const processQueries = {
  useGetProcess: () => {},
};

export const processMutaions = {
  useCreateProcess: ({ handleSuccess, postId }: { handleSuccess: () => void; postId: number }) => {
    const queryClient = useQueryClient();
    const invalidateQueries = () => {
      queryClient.invalidateQueries({ queryKey: ['dashboard', postId] });
    };

    return useMutation({
      mutationFn: (params: { dashboardId: number; orderIndex: number; name: string; description?: string }) =>
        processApis.create(params),
      onSuccess: () => {
        invalidateQueries();
        handleSuccess();
      },
      onError: () => {
        alert('프로세스 추가에 실패했습니다.');
      },
    });
  },

  useModifyProcess: (postId: number) => {
    const queryClient = useQueryClient();
    const invalidateQueries = () => {
      queryClient.invalidateQueries({ queryKey: ['dashboard', postId] });
    };

    return useMutation({
      mutationFn: (params: { processId: number; name: string; description?: string }) => processApis.modify(params),
      onSuccess: () => {
        invalidateQueries();
        alert('프로세스 수정에 성공했습니다.');
      },
      onError: () => {
        alert('프로세스 수정에 실패했습니다.');
      },
    });
  },

  useDeleteProcess: (postId: number) => {
    const queryClient = useQueryClient();
    const invalidateQueries = () => {
      queryClient.invalidateQueries({ queryKey: ['dashboard', postId] });
    };

    return useMutation({
      mutationFn: (processId: number) => processApis.delete({ processId }),
      onSuccess: () => {
        invalidateQueries();
      },
      onError: () => {
        alert('프로세스 삭제에 실패했습니다.');
      },
    });
  },
};
