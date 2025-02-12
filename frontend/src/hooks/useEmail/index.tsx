import emailApis from '@api/domain/email';
import { useToast } from '@contexts/ToastContext';
import { Email } from '@customTypes/email';
import QUERY_KEYS from '@hooks/queryKeys';
import { useMutation, useQueryClient } from '@tanstack/react-query';

export default function useEmail(onSuccess?: () => void) {
  const { success } = useToast();
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (prop: { clubId: string; applicantIds: number[]; subject: string; content: string }) =>
      emailApis.send(prop),
    onMutate: async ({ clubId, applicantIds, subject, content }) => {
      await queryClient.cancelQueries({ queryKey: [QUERY_KEYS.EMAIL_HISTORY, clubId, applicantIds[0]] });

      const previousEmailHistory = queryClient.getQueryData([QUERY_KEYS.EMAIL_HISTORY, clubId, applicantIds[0]]);

      queryClient.setQueryData(
        [QUERY_KEYS.EMAIL_HISTORY, clubId, applicantIds[0]],
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
      if (onSuccess) onSuccess();
      success('메일 전송에 성공했습니다');

      const FIVE_SECONDS = 5000;
      setTimeout(() => {
        queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.EMAIL_HISTORY, clubId, applicantIds[0]] });
      }, FIVE_SECONDS);
    },
    onError: (_, { clubId, applicantIds }, context) => {
      if (context && context.previousEmailHistory) {
        queryClient.setQueryData([QUERY_KEYS.EMAIL_HISTORY, clubId, applicantIds[0]], context.previousEmailHistory);
      }
    },
  });
}
