import { Applicant } from '@components/recruitmentPost/ApplyForm';
import { Branded } from '@customTypes/utilTypes';
import ValidationError from '@utils/errors/ValidationError';

type EmailAddress = Branded<string, 'EmailAddress'>;
export const isValidEmail = (email: string): email is EmailAddress => {
  const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  return emailRegex.test(email);
};

type PhoneNumber = Branded<string, 'PhoneNumber'>;
export const isValidPhoneNumber = (phone: string): phone is PhoneNumber => {
  const phoneRegex = /^\d{3}-\d{3,4}-\d{4}$/;
  return phoneRegex.test(phone);
};

export const validateApplicant = (applicant: Applicant) => {
  if (!isValidEmail(applicant.email)) {
    throw new ValidationError({ inputName: 'email', message: '이메일을 확인해 주세요.' });
  }

  if (!isValidPhoneNumber(applicant.phone)) {
    throw new ValidationError({ inputName: 'phone', message: '전화번호를 확인해 주세요.' });
  }
};
