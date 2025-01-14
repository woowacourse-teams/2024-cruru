import emailApis from '@api/domain/email';
import { useToast } from '@contexts/ToastContext';
import QUERY_KEYS from '@hooks/queryKeys';
import { useMutation, useQueryClient } from '@tanstack/react-query';

export default function useEmail(onSuccess?: () => void) {
  const { success } = useToast();
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (prop: { clubId: string; applicantIds: number[]; subject: string; content: string }) =>
      emailApis.send(prop),
    onSuccess: () => {
      if (onSuccess) onSuccess();
      success('메일 전송에 성공했습니다!');
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.EMAIL_HISTORY] });
    },
  });
}
