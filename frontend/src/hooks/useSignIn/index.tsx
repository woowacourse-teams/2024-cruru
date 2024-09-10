import authApi from '@api/domain/auth';
import useClubId from '@hooks/service/useClubId';
import { useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { routes } from '@router/path';

export default function useSignIn() {
  const navigate = useNavigate();
  const { saveClubId } = useClubId();

  const signInMutate = useMutation({
    mutationFn: ({ email, password }: { email: string; password: string }) => authApi.login({ email, password }),
    onSuccess: ({ clubId }) => {
      saveClubId(clubId);
      navigate(routes.dashboard.list());
    },
  });

  return {
    signInMutate,
  };
}
