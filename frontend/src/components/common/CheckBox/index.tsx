import { HiCheck } from 'react-icons/hi';
import type { StyleProps } from './styles';
import S from './styles';

interface CheckBoxProps extends StyleProps {
  onToggle: () => void;
}

export default function CheckBox({ width = '1.6rem', isChecked, onToggle, isDisabled = false }: CheckBoxProps) {
  return (
    <S.CheckBoxContainer
      width={width}
      isChecked={isChecked}
      onClick={onToggle}
      isDisabled={isDisabled}
    >
      <S.IconWrapper isDisabled={isDisabled}>{isChecked && <HiCheck />}</S.IconWrapper>
    </S.CheckBoxContainer>
  );
}
