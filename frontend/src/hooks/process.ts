import processApis from '@api/process';
import { useMutation, useQueryClient } from '@tanstack/react-query';

// 제안: 이런식으로 리팩토링 하는 건 어떨지?
export const processQueries = {
  useGetProcess: () => {},
};

export const processMutaions = {
  useCreateProcess: (params: { dashboardId: number; orderIndex: number; name: string; description?: string }) => {
    // TODO: useInvalidateQueries를 사용하는 것으로 리팩토링
    const queryClient = useQueryClient();
    const invalidateQueries = () => {
      queryClient.invalidateQueries({ queryKey: ['dashboard', params.dashboardId] });
    };

    return useMutation({
      mutationFn: () => processApis.create({ ...params }),
      onSuccess: () => {
        invalidateQueries();
      },
    });
  },
};
