import { RefCallback } from 'react';
import { HiOutlineX } from 'react-icons/hi';
import Radio from '@components/common/Radio';
import S from './style';

interface InputAttrsProps extends React.InputHTMLAttributes<HTMLInputElement> {
  ref?: RefCallback<HTMLInputElement>;
}

interface RadioInputOptionProps {
  isChecked?: boolean;
  isDisabled?: boolean;
  isDeleteBtn?: boolean;
  onCheck?: () => void;
  onDeleteBtnClick?: () => void;
  inputAttrs: InputAttrsProps;
}

export default function RadioInputOption({
  isChecked = false,
  isDisabled = false,
  isDeleteBtn = true,
  onDeleteBtnClick,
  onCheck = () => {},
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
      {isDeleteBtn ? (
        <S.DeleteBtn onClick={onDeleteBtnClick}>
          <HiOutlineX />
        </S.DeleteBtn>
      ) : (
        <S.Empty />
      )}
    </S.Container>
  );
}
