import {
  EVALUATION_CONTENT_MAX_LENGTH,
  EVALUATION_EVALUATOR_MAX_LENGTH,
} from '@components/ApplicantModal/ApplicantEvalInfo/constants';
import ValidationError from '@utils/errors/ValidationError';

export const validateEvaluator = (evaluator: string) => {
  if (evaluator.length > EVALUATION_EVALUATOR_MAX_LENGTH) {
    throw new ValidationError({
      inputName: 'evaluator',
      message: `평가자 이름은 ${EVALUATION_EVALUATOR_MAX_LENGTH}자 이내로 입력해주세요.`,
    });
  }

  const trimmedEvaluator = evaluator.trim();

  if (evaluator.length > 0 && trimmedEvaluator.length === 0) {
    throw new ValidationError({
      inputName: 'evaluator',
      message: '평가자 이름에 공백만 입력하는 것은 허용되지 않습니다.',
    });
  }

  if (trimmedEvaluator.length > 0 && /\s{2,}/.test(trimmedEvaluator)) {
    throw new ValidationError({
      inputName: 'evaluator',
      message: '평가자 이름에 연속된 공백을 사용할 수 없습니다.',
    });
  }

  const allowedEvaluatorPattern = /^[a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣]+([ ]?[a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣]+)*$/;

  if (trimmedEvaluator.length > 0 && !allowedEvaluatorPattern.test(trimmedEvaluator)) {
    throw new ValidationError({
      inputName: 'name',
      message: '평가자 이름에는 한글/영문과 공백 문자만 허용됩니다.',
    });
  }
};

export const validateEvalContent = (title: string) => {
  if (title.length > EVALUATION_CONTENT_MAX_LENGTH) {
    throw new ValidationError({
      inputName: 'content',
      message: `평가는 최대 ${EVALUATION_CONTENT_MAX_LENGTH}자까지 입력 가능합니다.`,
    });
  }
};
