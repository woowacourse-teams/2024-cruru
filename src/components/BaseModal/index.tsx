import { PropsWithChildren, useEffect, useRef } from 'react';
import { useModal } from '@/context/ModalContext';
import S from './styled';

export interface IModalProps extends PropsWithChildren {
  isOpen?: boolean;
}

export default function BaseModal({ isOpen: isOpenInit = false, children }: IModalProps) {
  const dialogRef = useRef<HTMLDialogElement | null>(null);
  const { isOpen, open, close } = useModal();

  useEffect(() => {
    if (isOpenInit) open();
  }, [isOpenInit, open]);

  useEffect(() => {
    if (dialogRef.current) {
      if (isOpen && !dialogRef.current.open) {
        dialogRef.current.showModal();
      }
      if (!isOpen && dialogRef.current.open) {
        dialogRef.current.close();
      }
    }
  }, [isOpen]);

  useEffect(() => {
    const handleKeyDown = (event: KeyboardEvent) => {
      if (event.key === 'Escape') close();
    };
    document.addEventListener('keydown', handleKeyDown);
    return () => document.removeEventListener('keydown', handleKeyDown);
  }, [close]);

  const handleOutsideClick = (event: React.MouseEvent<HTMLDialogElement, MouseEvent>) => {
    if (event.target === dialogRef.current) close();
  };

  return (
    <S.ModalOverlay
      ref={dialogRef}
      onClick={handleOutsideClick}
    >
      <div>{children}</div>
    </S.ModalOverlay>
  );
}
