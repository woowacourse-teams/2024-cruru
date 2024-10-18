import { HiCheck } from 'react-icons/hi';
import type { StyleProps } from './styles';
import S from './styles';

interface CheckBoxProps extends StyleProps {
  id?: string;
  onToggle: (checked: boolean) => void;
  name?: string;
  required?: boolean;
}

export default function CheckBox({
  id,
  width = '1.6rem',
  isChecked,
  onToggle,
  isDisabled = false,
  name,
  required = false,
}: CheckBoxProps) {
  return (
    <S.CheckBoxContainer
      width={width}
      isChecked={isChecked}
      onClick={() => onToggle(!isChecked)}
      isDisabled={isDisabled}
    >
      <S.HiddenCheckbox
        id={id}
        type="checkbox"
        checked={isChecked}
        onChange={(e: React.ChangeEvent<HTMLInputElement>) => onToggle(e.target.checked)}
        disabled={isDisabled}
        name={name}
        required={required}
      />
      <S.IconWrapper isDisabled={isDisabled}>{isChecked && <HiCheck />}</S.IconWrapper>
    </S.CheckBoxContainer>
  );
}
