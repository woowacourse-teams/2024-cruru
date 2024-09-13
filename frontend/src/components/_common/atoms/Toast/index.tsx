import { ToastType } from '@contexts/ToastContext';
import { useEffect, useState } from 'react';
import S from './style';

interface ToastProps {
  message: string;
  type?: ToastType;
  visible: boolean;
}

export default function Toast({ message, type = 'default', visible }: ToastProps) {
  return (
    <S.ToastContainer
      type={type}
      visible={visible}
    >
      <S.Message>{message}</S.Message>
    </S.ToastContainer>
  );
}

export function ToastModal({ message, type = 'default' }: Omit<ToastProps, 'visible'>) {
  const [visible, setVisible] = useState(true);

  useEffect(() => {
    const timer = setTimeout(() => {
      setVisible(false);
    }, 3000);

    return () => clearTimeout(timer);
  }, []);

  return (
    <Toast
      message={message}
      type={type}
      visible={visible}
    />
  );
}
