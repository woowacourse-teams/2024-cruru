import { Branded } from '@customTypes/utilTypes';

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
