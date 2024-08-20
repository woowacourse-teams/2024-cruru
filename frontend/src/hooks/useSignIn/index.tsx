import authApi from '@api/domain/auth';
import { useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';

export default function useSignIn() {
  const navigate = useNavigate();
  const signInMutate = useMutation({
    mutationFn: ({ email, password }: { email: string; password: string }) => authApi.login({ email, password }),
    onSuccess: () => {
      // 로그인 성공 후 수행할 작업이 있다면 여기에 작성
      // queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.USER_DATA] });
      navigate('dashboard/1/posts');
    },
    onError: (error) => {
      window.alert(error.message);
    },
  });

  return {
    signInMutate,
  };
}
