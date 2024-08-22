import membersApi from '@api/domain/member';
import { useToast } from '@contexts/ToastContext';
import { useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';

export default function useSignUp() {
  const navigate = useNavigate();
  const { success } = useToast();
  const signUpMutate = useMutation({
    mutationFn: ({
      clubName,
      email,
      password,
      phone,
    }: {
      clubName: string;
      email: string;
      password: string;
      phone: string;
    }) => membersApi.signUp({ clubName, email, password, phone }),
    onSuccess: () => {
      success('회원가입이 완료되었습니다. 로그인해주세요.');
      navigate('/sign-in');
    },
  });

  return {
    signUpMutate,
  };
}
