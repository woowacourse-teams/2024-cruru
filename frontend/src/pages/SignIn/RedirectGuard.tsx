import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { routes } from '@router/path';
import useClubId from '@hooks/service/useClubId';

interface RenderGuardProps {
  children: React.ReactNode;
}

export default function RedirectGuard({ children }: RenderGuardProps) {
  const navigate = useNavigate();
  const { isClubIdExist } = useClubId();

  useEffect(() => {
    if (isClubIdExist) {
      navigate(routes.dashboard.list(), { replace: true });
    }
  }, [isClubIdExist, navigate]);

  if (isClubIdExist) {
    return null;
  }

  return children;
}
