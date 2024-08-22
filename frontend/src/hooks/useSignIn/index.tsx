import authApi from '@api/domain/auth';
import { useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';

export default function useSignIn() {
  const navigate = useNavigate();

  const signInMutate = useMutation({
    mutationFn: ({ email, password }: { email: string; password: string }) => authApi.login({ email, password }),
    onSuccess: ({ clubId }) => {
      navigate(`/dashboard/${clubId}/posts`);
    },
  });

  return {
    signInMutate,
  };
}
