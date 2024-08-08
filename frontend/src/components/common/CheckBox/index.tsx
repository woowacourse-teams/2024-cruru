import { HiCheck } from 'react-icons/hi';
import type { StyleProps } from './styles';
import S from './styles';

interface CheckBoxProps extends StyleProps {
  onToggle: (checked: boolean) => void;
  name?: string;
}

export default function CheckBox({ width = '1.6rem', isChecked, onToggle, isDisabled = false, name }: CheckBoxProps) {
  return (
    <S.CheckBoxContainer
      width={width}
      isChecked={isChecked}
      onClick={() => onToggle(!isChecked)}
      isDisabled={isDisabled}
    >
      <S.HiddenCheckbox
        type="checkbox"
        checked={isChecked}
        onChange={(e: React.ChangeEvent<HTMLInputElement>) => onToggle(e.target.checked)}
        disabled={isDisabled}
        name={name}
      />
      <S.IconWrapper isDisabled={isDisabled}>{isChecked && <HiCheck />}</S.IconWrapper>
    </S.CheckBoxContainer>
  );
}
