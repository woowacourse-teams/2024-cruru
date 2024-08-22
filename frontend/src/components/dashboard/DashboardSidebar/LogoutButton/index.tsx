import useSignOut from '@hooks/useSignOut';
import Spinner from '@components/common/Spinner';
import S from './style';

export default function LogoutButton() {
  const { mutate: logout, isPending } = useSignOut();

  return (
    <S.LogoutButton onClick={() => logout()}>
      {isPending ? (
        <Spinner
          width={40}
          color="primary"
        />
      ) : (
        '로그아웃'
      )}
    </S.LogoutButton>
  );
}
