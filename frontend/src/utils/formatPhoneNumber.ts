export const formatPhoneNumber = (phone: string) => {
  const cleaned = phone.replace(/-/g, '');

  if (cleaned.length <= 3) {
    return cleaned;
  }
  if (cleaned.length <= 7) {
    return cleaned.replace(/(\d{3})(\d{1,4})/, '$1-$2');
  }
  return cleaned.replace(/(\d{3})(\d{3,4})(\d{1,4})/, '$1-$2-$3');
};
