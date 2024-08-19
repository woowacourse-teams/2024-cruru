import { HTMLAttributes, useState } from 'react';
import InputField from '@components/common/InputField';
import { FaEyeSlash, FaEye } from 'react-icons/fa';
import S from './style';

export default function PasswordInput(props: HTMLAttributes<HTMLInputElement>) {
  const [isShow, setIsShow] = useState(false);
  const [isCapsLockOn, setIsCapsLockOn] = useState(false);
  const [isNumLockOn, setIsNumLockOn] = useState(true);

  const handleIconClick = () => {
    setIsShow((prev) => !prev);
  };

  const setModifierState = (event: React.KeyboardEvent<HTMLInputElement>) => {
    setIsCapsLockOn(event.getModifierState('CapsLock'));
    setIsNumLockOn(event.getModifierState('NumLock'));
  };

  const handleKeyUp = (event: React.KeyboardEvent<HTMLInputElement>) => {
    setModifierState(event);
    // eslint-disable-next-line react/destructuring-assignment
    if (props?.onKeyUp) props.onKeyUp(event);
  };

  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    setModifierState(event);
    // eslint-disable-next-line react/destructuring-assignment
    if (props?.onKeyDown) props.onKeyDown(event);
  };

  const handelBlur = (event: React.FocusEvent<HTMLInputElement, Element>) => {
    setIsCapsLockOn(false);
    setIsNumLockOn(false);

    // eslint-disable-next-line react/destructuring-assignment
    if (props?.onBlur) props.onBlur(event);
  };

  return (
    <S.PasswordInputContainer>
      <InputField
        label="비밀번호"
        placeholder="비밀번호"
        type={isShow ? 'text' : 'password'}
        error={isCapsLockOn ? 'Caps Lock이 켜져 있습니다.' : !isNumLockOn ? 'Num Lock이 꺼져 있습니다.' : undefined}
        {...props}
        onKeyDown={handleKeyDown}
        onKeyUp={handleKeyUp}
        onBlur={handelBlur}
      />
      <S.IconButton onClick={handleIconClick}>{isShow ? <FaEye /> : <FaEyeSlash />}</S.IconButton>
    </S.PasswordInputContainer>
  );
}
