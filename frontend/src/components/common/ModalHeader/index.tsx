import { useModal } from '@contexts/ModalContext';
import { MdClose } from 'react-icons/md';
import IconButton from '../IconButton';
import S from './style';

interface ModalHeaderProps {
  title: string;
}

export default function ModalHeader({ title }: ModalHeaderProps) {
  const { close } = useModal();
  return (
    <S.Container>
      <S.Title>{title}</S.Title>
      <IconButton
        size="sm"
        color="white"
        outline={false}
        onClick={close}
      >
        <MdClose />
      </IconButton>
    </S.Container>
  );
}
