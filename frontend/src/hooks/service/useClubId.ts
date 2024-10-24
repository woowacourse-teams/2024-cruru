import { useSyncExternalStore } from 'react';
import { useNavigate } from 'react-router-dom';
import { useToast } from '@contexts/ToastContext';
import { routes } from '@router/path';

const CLUB_ID_KEY = 'clubId';

const subscribe = (callback: () => void) => {
  window.addEventListener('storage', callback);
  return () => window.removeEventListener('storage', callback);
};

const getSnapshot = () => localStorage.getItem(CLUB_ID_KEY);

export default function useClubId() {
  const { error } = useToast();
  const navigate = useNavigate();

  const saveClubId = (clubId: string) => {
    localStorage.setItem(CLUB_ID_KEY, clubId);
  };

  const getClubId = () => {
    const clubId = getSnapshot();

    if (!clubId) {
      error('사용자 정보가 만료되었습니다. 다시 로그인해주세요.');
      navigate(routes.home());
    }

    return clubId;
  };

  const clearClubId = () => {
    localStorage.removeItem(CLUB_ID_KEY);
  };

  const clubId = useSyncExternalStore(subscribe, getSnapshot);

  return {
    saveClubId,
    getClubId,
    clearClubId,
    clubId,
    isClubIdExist: !!clubId,
  };
}
