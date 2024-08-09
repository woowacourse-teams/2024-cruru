import { HiChevronUp, HiChevronDown, HiOutlineTrash } from 'react-icons/hi';
import { QuestionControlActionType } from '@customTypes/dashboard';
import S from './style';

interface QuestionControllerProps {
  onClickControlButton: (type: QuestionControlActionType) => void;
}

interface ControlButtonType {
  type: QuestionControlActionType;
  icon: React.ReactNode;
  isDeleteButton: boolean;
}

export default function QuestionController({ onClickControlButton }: QuestionControllerProps) {
  const buttons: ControlButtonType[] = [
    { type: 'moveUp', icon: <HiChevronUp />, isDeleteButton: false },
    { type: 'moveDown', icon: <HiChevronDown />, isDeleteButton: false },
    { type: 'delete', icon: <HiOutlineTrash />, isDeleteButton: true },
  ];

  return (
    <S.ButtonGroup>
      {buttons.map((button) => (
        <S.Button
          key={button.type}
          type="button"
          onClick={() => onClickControlButton(button.type)}
          isDeleteButton={button.isDeleteButton}
        >
          {button.icon}
        </S.Button>
      ))}
    </S.ButtonGroup>
  );
}
