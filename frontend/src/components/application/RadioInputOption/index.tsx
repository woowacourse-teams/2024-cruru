import { HiOutlineX } from 'react-icons/hi';
import Radio from '@components/common/Radio';
import S from './style';

interface RadioInputOptionProps {
  isChecked?: boolean;
  isDisabled: boolean;
  onCheck: () => void;
  onDeleteBtnClick: () => void;
  inputAttrs: React.InputHTMLAttributes<HTMLInputElement>;
}

export default function RadioInputOption({
  isChecked = false,
  isDisabled,
  onDeleteBtnClick,
  onCheck,
  inputAttrs,
}: RadioInputOptionProps) {
  return (
    <S.Container>
      <Radio
        isChecked={isChecked}
        isDisabled={isDisabled}
        onToggle={onCheck}
      />

      <S.Input
        {...inputAttrs}
        placeholder="옵션을 입력하세요."
      />

      <S.DeleteBtn onClick={onDeleteBtnClick}>
        <HiOutlineX />
      </S.DeleteBtn>
    </S.Container>
  );
}
