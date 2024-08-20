/* eslint-disable react/destructuring-assignment */
import { ComponentProps, useState } from 'react';
import InputField from '@components/common/InputField';
import { FaEyeSlash, FaEye } from 'react-icons/fa';
import { HiOutlineCheckCircle, HiOutlineMinusCircle } from 'react-icons/hi2';
import S from './style';

interface PasswordInput extends ComponentProps<'input'> {
  setPasswordValid: (bol: boolean) => void;
  error: boolean;
}

export default function PasswordInput({ setPasswordValid, ...props }: PasswordInput) {
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
      const specialCharRegex = /[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]/;
      return {
        ...prev,
        length: { ...prev.length, isValid: password.length >= 8 && password.length <= 32 },
        letter: { ...prev.letter, isValid: /[A-Za-z]/.test(password) },
        number: { ...prev.number, isValid: /\d/.test(password) },
        specialChar: { ...prev.specialChar, isValid: specialCharRegex.test(password) },
      };
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
    setPasswordValid(Object.values(validObject).some(({ isValid }) => !isValid));
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
        error={
          props?.error && !isFocus ? '패스워드를 확인하세요.' : isCapsLockOn ? 'Caps Lock이 켜져 있습니다.' : undefined
        }
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
