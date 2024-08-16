import ValidationError from '@utils/errors/ValidationError';
import { isEmptyString } from './common';

const RECRUITMENT_TITLE_MAX_LENGTH = 32;

export const validateTitle = (title: string) => {
  if (isEmptyString(title)) {
    throw new ValidationError({ inputName: 'title', message: '공고의 제목을 입력해 주세요.' });
  }

  if (title.length > RECRUITMENT_TITLE_MAX_LENGTH) {
    throw new ValidationError({
      inputName: 'title',
      message: `공고는 최대 ${RECRUITMENT_TITLE_MAX_LENGTH}자까지 입력 가능합니다.`,
    });
  }
};
