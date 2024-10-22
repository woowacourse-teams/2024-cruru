import emailApis from '@api/domain/email';
import { useToast } from '@contexts/ToastContext';
import { useMutation } from '@tanstack/react-query';

export default function useEmailVerify() {
  const { success } = useToast();

  const { mutate: postVerifyEmailMutate } = useMutation({
    mutationFn: (prop: { email: string }) => emailApis.postVerifyMail(prop),
    onSuccess: () => {
      success('메일 전송에 성공했습니다!');
    },
  });

  const { mutate: confirmEmailVerifyMutate } = useMutation({
    mutationFn: (prop: { email: string; verificationCode: string }) => emailApis.confirmVerifyCode(prop),
    onSuccess: () => {
      success('이메일 인증에 성공했습니다!');
    },
  });

  return { postVerifyEmailMutate, confirmEmailVerifyMutate };
}
