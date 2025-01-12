import ValidationError from '@utils/errors/ValidationError';
import {
  EVALUATION_CONTENT_MAX_LENGTH,
  EVALUATION_EVALUATOR_MAX_LENGTH,
} from '@components/ApplicantModal/ApplicantEvalInfo/constants';
import { validateEvaluator, validateEvalContent } from './evaluation';

describe('validateEvaluator', () => {
  it('유효한 평가자 이름을 입력하면 에러가 발생하지 않는다.', () => {
    const validNames = ['홍길동', 'John Doe', '김철수', 'Alice', '박 하나', 'Mary Jane'];

    validNames.forEach((name) => {
      expect(() => validateEvaluator(name)).not.toThrow();
    });
  });

  it('최대 길이를 초과하면 에러가 발생한다.', () => {
    const longName = 'a'.repeat(EVALUATION_EVALUATOR_MAX_LENGTH + 1);

    expect(() => validateEvaluator(longName)).toThrow(ValidationError);
    expect(() => validateEvaluator(longName)).toThrow(
      `평가자 이름은 ${EVALUATION_EVALUATOR_MAX_LENGTH}자 이내로 입력해주세요.`,
    );
  });

  it('공백만 입력하면 에러가 발생한다.', () => {
    const whitespaceOnly = '   ';

    expect(() => validateEvaluator(whitespaceOnly)).toThrow(ValidationError);
    expect(() => validateEvaluator(whitespaceOnly)).toThrow('평가자 이름에 공백만 입력하는 것은 허용되지 않습니다.');
  });

  it('이름 단어 사이에 2칸 이상 공백이 포함되면 에러가 발생한다..', () => {
    const invalidNames = ['홍  길동', 'John   Doe', 'Alice    Jung'];

    invalidNames.forEach((name) => {
      expect(() => validateEvaluator(name)).toThrow(ValidationError);
      expect(() => validateEvaluator(name)).toThrow('평가자 이름에 연속된 공백을 사용할 수 없습니다.');
    });
  });

  it('허용되지 않는 문자가 포함되면 에러가 발생한다.', () => {
    const invalidNames = ['Hong!길동', 'John@Doe', '김철수#', '123Alice', '박*하나', 'Mary&Jane'];

    invalidNames.forEach((name) => {
      expect(() => validateEvaluator(name)).toThrow(ValidationError);
      expect(() => validateEvaluator(name)).toThrow('평가자 이름에는 한글/영문과 공백 문자만 허용됩니다.');
    });
  });

  it('빈 문자열은 허용된다.', () => {
    expect(() => validateEvaluator('')).not.toThrow();
  });
});

describe('validateEvalContent', () => {
  it('평가 내용이 최대 길이를 초과하면 에러가 발생한다.', () => {
    const longContent = 'a'.repeat(EVALUATION_CONTENT_MAX_LENGTH + 1);

    expect(() => validateEvalContent(longContent)).toThrow(ValidationError);
    expect(() => validateEvalContent(longContent)).toThrow(
      `평가는 최대 ${EVALUATION_CONTENT_MAX_LENGTH}자까지 입력 가능합니다.`,
    );
  });
});
