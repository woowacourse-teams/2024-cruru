import { EVALUATION_CONTENT_MAX_LENGTH } from '@components/ApplicantModal/ApplicantEvalInfo/constants';
import ValidationError from '@utils/errors/ValidationError';

export const validateEvalContent = (title: string) => {
  if (title.length > EVALUATION_CONTENT_MAX_LENGTH) {
    throw new ValidationError({
      inputName: 'content',
      message: `평가는 최대 ${EVALUATION_CONTENT_MAX_LENGTH}자까지 입력 가능합니다.`,
    });
  }
};
