import processApis from '@api/domain/process';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useToast } from '@contexts/ToastContext';
import QUERY_KEYS from './queryKeys';

export const processQueries = {
  useGetProcess: () => {},
};

export const processMutations = {
  useCreateProcess: ({
    handleSuccess,
    dashboardId,
    postId,
  }: {
    handleSuccess: () => void;
    dashboardId: string;
    postId: string;
  }) => {
    const queryClient = useQueryClient();
    const invalidateQueries = () => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.DASHBOARD, dashboardId, postId] });
    };

    return useMutation({
      mutationFn: (params: { dashboardId: number; orderIndex: number; name: string; description?: string }) =>
        processApis.create(params),
      onSuccess: () => {
        invalidateQueries();
        handleSuccess();
      },
    });
  },

  useModifyProcess: ({ dashboardId, postId }: { dashboardId: string; postId: string }) => {
    const queryClient = useQueryClient();
    const invalidateQueries = () => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.DASHBOARD, dashboardId, postId] });
    };

    const toast = useToast();

    return useMutation({
      mutationFn: (params: { processId: number; name: string; description?: string }) => processApis.modify(params),
      onSuccess: () => {
        invalidateQueries();
        toast.success('프로세스 수정에 성공했습니다.');
      },
    });
  },

  useDeleteProcess: ({ dashboardId, postId }: { dashboardId: string; postId: string }) => {
    const queryClient = useQueryClient();
    const invalidateQueries = () => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.DASHBOARD, dashboardId, postId] });
    };

    return useMutation({
      mutationFn: (processId: number) => processApis.delete({ processId }),
      onSuccess: () => {
        invalidateQueries();
      },
    });
  },
};
