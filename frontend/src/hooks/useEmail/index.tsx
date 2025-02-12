import emailApis from '@api/domain/email';
import { useToast } from '@contexts/ToastContext';
import { Email } from '@customTypes/email';
import { createEmailHistoryQueryKey } from '@hooks/useGetEmailHistory';
import { useMutation, useQueryClient } from '@tanstack/react-query';

export default function useEmail(onSuccess?: () => void) {
  const { success, error } = useToast();
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (prop: { clubId: string; applicantIds: number[]; subject: string; content: string }) =>
      emailApis.send(prop),

    onMutate: async ({ clubId, applicantIds, subject, content }) => {
      // 데이터 충돌을 방지하기 위해 cancelQueries 사용
      await queryClient.cancelQueries({ queryKey: createEmailHistoryQueryKey(clubId, applicantIds[0]) });

      // 낙관적 업데이트
      const previousEmailHistory = queryClient.getQueryData(createEmailHistoryQueryKey(clubId, applicantIds[0]));
      queryClient.setQueryData(
        createEmailHistoryQueryKey(clubId, applicantIds[0]),
        (old: { emailHistoryResponses: Email[] }) => ({
          emailHistoryResponses: [
            ...old.emailHistoryResponses,
            { subject, content, createdDate: new Date().toISOString(), isSucceed: false, optimistic: true },
          ],
        }),
      );

      return { previousEmailHistory };
    },

    onSuccess: (_, { applicantIds, clubId }) => {
      const FIVE_SECONDS = 5000;
      setTimeout(() => {
        if (onSuccess) onSuccess();
        success('메일 전송에 성공했습니다');

        queryClient.invalidateQueries({ queryKey: createEmailHistoryQueryKey(clubId, applicantIds[0]) });
      }, FIVE_SECONDS);
    },

    onError: (_, { clubId, applicantIds }, context) => {
      error('메일 전송에 실패했습니다');

      if (context) {
        queryClient.setQueryData(createEmailHistoryQueryKey(clubId, applicantIds[0]), context.previousEmailHistory);
      }
    },
  });
}
