import { useToast } from '@contexts/ToastContext';

const CLUB_ID_KEY = 'clubId';

export default function useClubId() {
  const { error } = useToast();

  const saveClubId = (clubId: string) => {
    localStorage.setItem(CLUB_ID_KEY, clubId);
  };

  const getClubId = () => {
    const clubId = localStorage.getItem(CLUB_ID_KEY);

    if (!clubId) {
      error('세션이 만료되었습니다. 다시 로그인해주세요.');
      return null;
    }

    return clubId;
  };

  const clearClubId = () => {
    localStorage.removeItem(CLUB_ID_KEY);
  };

  return {
    saveClubId,
    getClubId,
    clearClubId,
  };
}
