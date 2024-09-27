/* eslint-disable prefer-arrow-callback */
import { ComponentPropsWithRef } from 'react';
import { HiOutlineX } from 'react-icons/hi';
import CheckBox from '@components/_common/atoms/CheckBox';
import S from './style';

interface CheckBoxOptionProps {
  isChecked?: boolean;
  isDisabled?: boolean;
  isDeleteBtn?: boolean;
  onCheck?: () => void;
  onDeleteBtnClick: () => void;
  inputAttrs: ComponentPropsWithRef<'input'>;
}

export default function CheckBoxOption({
  isChecked = false,
  isDisabled = false,
  isDeleteBtn = true,
  onDeleteBtnClick,
  onCheck = () => {},
  inputAttrs,
}: CheckBoxOptionProps) {
  return (
    <S.Container>
      <CheckBox
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
