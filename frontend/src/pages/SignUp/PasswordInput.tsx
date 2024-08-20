/* eslint-disable react/destructuring-assignment */
import { ComponentProps, useState } from 'react';
import InputField from '@components/common/InputField';
import { FaEyeSlash, FaEye } from 'react-icons/fa';
import { HiOutlineCheckCircle, HiOutlineMinusCircle } from 'react-icons/hi2';
import S from './style';

export default function PasswordInput(props: ComponentProps<'input'>) {
  const [isShow, setIsShow] = useState(false);
  const [isCapsLockOn, setIsCapsLockOn] = useState(false);
  const [isFocus, setIsFocus] = useState(false);
  const [validObject, setValidObject] = useState<Record<string, { isValid: boolean; msg: string }>>({
    length: { isValid: false, msg: '비밀번호는 최소 8자 이상, 최대 32자 이하여야 합니다.' },
    letter: { isValid: false, msg: '비밀번호는 영문가 1개 이상 포함되어야 합니다.' },
    number: { isValid: false, msg: '비밀번호는 숫자가 1개 이상 포함되어야 합니다.' },
    specialChar: { isValid: false, msg: '비밀번호는 특수 문자가 1개 이상 포함되어야 합니다.' },
  });

  const validatePassword = (password: string) => {
    setValidObject((prev) => {
      const newObj = { ...prev };
      // 비밀번호 길이 체크 (최소 8자, 최대 32자)
      newObj.length.isValid = password.length >= 8 && password.length <= 32;
      // 영어 문자가 포함되어 있는지 체크 (적어도 1자 이상)
      newObj.letter.isValid = /[A-Za-z]/.test(password);
      // 숫자가 포함되어 있는지 체크 (적어도 1개 이상)
      newObj.number.isValid = /\d/.test(password);
      // 특수문자가 포함되어 있는지 체크 (적어도 1개 이상)
      const specialCharRegex = /[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]/;
      newObj.specialChar.isValid = specialCharRegex.test(password);
      return newObj;
    });
  };

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { value } = event.target;
    validatePassword(value);

    if (props?.onChange) props.onChange(event);
  };

  const handleIconClick = () => {
    setIsShow((prev) => !prev);
  };

  const setModifierState = (event: React.KeyboardEvent<HTMLInputElement>) => {
    setIsCapsLockOn(event.getModifierState('CapsLock'));
  };

  const handleKeyUp = (event: React.KeyboardEvent<HTMLInputElement>) => {
    setModifierState(event);
    if (props?.onKeyUp) props.onKeyUp(event);
  };

  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    setModifierState(event);
    if (props?.onKeyDown) props.onKeyDown(event);
  };

  const handelBlur = (event: React.FocusEvent<HTMLInputElement, Element>) => {
    setIsCapsLockOn(false);
    setIsFocus(false);
    if (props?.onBlur) props.onBlur(event);
  };

  const handleFocus = () => {
    setIsFocus(true);
  };

  return (
    <S.PasswordInputContainer>
      <InputField
        {...props}
        label="비밀번호"
        placeholder="비밀번호"
        type={isShow ? 'text' : 'password'}
        error={isCapsLockOn ? 'Caps Lock이 켜져 있습니다.' : undefined}
        onKeyDown={handleKeyDown}
        onKeyUp={handleKeyUp}
        onBlur={handelBlur}
        onChange={handleChange}
        onFocus={handleFocus}
        required
      />

      {isFocus && (
        <S.MsgContainer>
          {Object.values(validObject).map(({ isValid, msg }) => (
            <S.MsgWrapper key={msg}>
              <S.CheckIcon isValid={isValid}>
                {isValid ? <HiOutlineCheckCircle /> : <HiOutlineMinusCircle />}
              </S.CheckIcon>
              <S.Message isValid={isValid}>{msg}</S.Message>
            </S.MsgWrapper>
          ))}
        </S.MsgContainer>
      )}

      <S.IconButton onClick={handleIconClick}>{isShow ? <FaEye /> : <FaEyeSlash />}</S.IconButton>
    </S.PasswordInputContainer>
  );
}
