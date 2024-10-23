import { useSyncExternalStore } from 'react';
import ApiError from '@api/ApiError';
import { useToast } from '@contexts/ToastContext';

const CLUB_ID_KEY = 'clubId';

const subscribe = (callback: () => void) => {
  window.addEventListener('storage', callback);
  return () => window.removeEventListener('storage', callback);
};

const getSnapshot = () => localStorage.getItem(CLUB_ID_KEY);

export default function useClubId() {
  const { error } = useToast();

  const saveClubId = (clubId: string) => {
    localStorage.setItem(CLUB_ID_KEY, clubId);
  };

  const getClubId = () => {
    const clubId = getSnapshot();

    if (!clubId) {
      error('세션이 만료되었습니다. 다시 로그인해주세요.');
      throw new ApiError({ message: '세션이 만료되었습니다. 다시 로그인해주세요.', statusCode: 401, method: 'GET' });
    }

    return clubId;
  };

  const clearClubId = () => {
    localStorage.removeItem(CLUB_ID_KEY);
  };

  const clubId = useSyncExternalStore(subscribe, getSnapshot, getSnapshot);

  return {
    saveClubId,
    getClubId,
    clearClubId,
    clubId,
    isClubIdExist: !!clubId,
  };
}
