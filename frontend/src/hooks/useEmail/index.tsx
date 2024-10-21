import emailApis from '@api/domain/email';
import { useToast } from '@contexts/ToastContext';
import { useMutation } from '@tanstack/react-query';

export default function useEmail() {
  const { success } = useToast();
  return useMutation({
    mutationFn: (prop: { clubId: string; applicantIds: number[]; subject: string; content: string }) =>
      emailApis.send(prop),
    onSuccess: () => {
      success('메일 전송에 성공했습니다!');
    },
  });
}
