import processApis from '@api/process';
import { DASHBOARD_ID } from '@constants/constants';
import { useMutation, useQueryClient } from '@tanstack/react-query';

// 제안: 이런식으로 리팩토링 하는 건 어떨지?
export const processQueries = {
  useGetProcess: () => {},
};

export const processMutaions = {
  useCreateProcess: ({ handleSuccess }: { handleSuccess: () => void }) => {
    // TODO: useInvalidateQueries를 사용하는 것으로 리팩토링
    const queryClient = useQueryClient();
    const invalidateQueries = () => {
      queryClient.invalidateQueries({ queryKey: ['dashboard', DASHBOARD_ID] });
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
};
