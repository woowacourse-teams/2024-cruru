import { Branded } from '@customTypes/utilTypes';
import ValidationError from '@utils/errors/ValidationError';
import { isEmptyString, isNumber } from './common';

type EmailAddress = Branded<string, 'EmailAddress'>;
const isValidEmail = (email: string): email is EmailAddress => {
  const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  return emailRegex.test(email);
};

type PhoneNumber = Branded<string, 'PhoneNumber'>;
const isValidPhoneNumber = (phone: string): phone is PhoneNumber => {
  const phoneRegex = /^\d{3}-\d{3,4}-\d{4}$/;
  return phoneRegex.test(phone);
};

export const validateName = {
  onBlur: (name: string) => {
    if (isEmptyString(name)) {
      throw new ValidationError({ inputName: 'name', message: '이름을 입력해 주세요.' });
    }
  },

  onChange: (name: string) => {
    if (/[^ㄱ-ㅎ가-힣a-zA-Z\s-]/.test(name)) {
      throw new ValidationError({ inputName: 'name', message: '한글, 영문, 공백, - 만 입력해 주세요.' });
    }
  },
};

export const validateEmail = {
  onBlur: (email: string) => {
    if (isEmptyString(email)) {
      throw new ValidationError({ inputName: 'email', message: '이메일을 입력해 주세요.' });
    }

    if (!isValidEmail(email)) {
      throw new ValidationError({ inputName: 'email', message: '이메일을 확인해 주세요.' });
    }
  },
};

export const validatePhoneNumber = {
  onBlur: (phone: string) => {
    if (isEmptyString(phone)) {
      throw new ValidationError({ inputName: 'phone', message: '전화번호를 입력해 주세요.' });
    }

    if (!isNumber(phone.replace(/-/g, ''))) {
      throw new ValidationError({ inputName: 'phone', message: '숫자만 입력해 주세요.' });
    }

    if (!isValidPhoneNumber(phone)) {
      throw new ValidationError({ inputName: 'phone', message: '전화번호를 확인해 주세요.' });
    }
  },

  onChange: (phone: string) => {
    if (!isNumber(phone.replace(/-/g, ''))) {
      throw new ValidationError({ inputName: 'phone', message: '숫자만 입력해 주세요.' });
    }
  },
};
