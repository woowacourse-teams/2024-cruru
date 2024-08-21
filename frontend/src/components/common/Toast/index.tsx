import S from './style';

interface ToastProps {
  message: string;
  type?: 'default' | 'success' | 'error' | 'primary';
}

export default function Toast({ message, type = 'default' }: ToastProps) {
  return (
    <S.ToastContainer type={type}>
      <S.Message>{message}</S.Message>
    </S.ToastContainer>
  );
}
