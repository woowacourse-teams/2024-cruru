import authApi from '@api/domain/auth';
import useClubId from '@hooks/service/useClubId';
import { useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { routes } from '@router/path';

export default function useSignOut() {
  const navigate = useNavigate();
  const { clearClubId } = useClubId();

  return useMutation({
    mutationFn: () => authApi.logout(),
    onSuccess: () => {
      clearClubId();
      navigate(routes.home());
    },
  });
}
