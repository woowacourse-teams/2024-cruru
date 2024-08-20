import membersApi from '@api/domain/member';
import { useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';

export default function useSignUp() {
  const navigate = useNavigate();
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
      window.alert('회원가입이 완료되었습니다. 로그인해주세요.');
      navigate('/sign-in');
    },
    onError: (error) => {
      window.alert(error.message);
    },
  });

  return {
    signUpMutate,
  };
}
