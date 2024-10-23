import emailApis from '@api/domain/email';
import { useToast } from '@contexts/ToastContext';
import { useMutation } from '@tanstack/react-query';

interface UseEmailVerifyProps {
  setIsSendVerifyEmail: (bol: boolean) => void;
  setIsVerify: (bol: boolean) => void;
}

export default function useEmailVerify({ setIsSendVerifyEmail, setIsVerify }: UseEmailVerifyProps) {
  const { success } = useToast();

  const { mutate: postVerifyEmailMutate, isPending: isPendingPostVerifyEmail } = useMutation({
    mutationFn: (prop: { email: string }) => emailApis.postVerifyMail(prop),
    onSuccess: () => {
      success('메일 전송에 성공했습니다. 이메일을 확인해주세요!');
      setIsSendVerifyEmail(true);
    },
  });

  const { mutate: confirmEmailVerificationMutate, isPending: isPendingConfirmEmailVerification } = useMutation({
    mutationFn: (prop: { email: string; verificationCode: string }) => emailApis.confirmVerifyCode(prop),
    onSuccess: () => {
      success('이메일 인증에 성공했습니다!');
      setIsVerify(true);
    },
  });

  return {
    postVerifyEmailMutate,
    isPendingPostVerifyEmail,
    confirmEmailVerificationMutate,
    isPendingConfirmEmailVerification,
  };
}
