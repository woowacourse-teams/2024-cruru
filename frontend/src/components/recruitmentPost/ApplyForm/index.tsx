import Button from '@components/_common/atoms/Button';
import InputField from '@components/_common/molecules/InputField';
import CustomQuestion from '@components/recruitmentPost/CustomQuestion';
import { FormEventHandler, useState } from 'react';

import { validateEmail, validateName, validatePhoneNumber } from '@domain/validations/apply';

import { ApplicantData, ApplyRequestBody, Question } from '@customTypes/apply';
import { applyMutations, applyQueries } from '@hooks/apply';
import useForm from '@hooks/utils/useForm';
import { useParams } from 'react-router-dom';

import CheckBox from '@components/_common/atoms/CheckBox';
import { useToast } from '@contexts/ToastContext';
import C from '../style';
import S from './style';
import { useAnswers } from './useAnswers';

interface ApplyFormProps {
  questions: Question[];
  isClosed: boolean;
}

export default function ApplyForm({ questions, isClosed }: ApplyFormProps) {
  const { applyFormId } = useParams<{ applyFormId: string }>() as { applyFormId: string };

  const { data: recruitmentPost } = applyQueries.useGetRecruitmentPost({ applyFormId: applyFormId ?? '' });
  const { mutate: apply } = applyMutations.useApply(applyFormId, recruitmentPost?.title ?? '');

  const {
    formData: applicant,
    register,
    hasErrors,
  } = useForm<ApplicantData>({
    initialValues: { name: '', email: '', phone: '' },
  });
  const { answers, changeHandler, isRequiredFieldsIncomplete } = useAnswers(questions);
  const [personalDataCollection, setPersonalDataCollection] = useState(false);

  const { error } = useToast();

  const handleSubmit: FormEventHandler<HTMLFormElement> = (e) => {
    e.preventDefault();

    if (isRequiredFieldsIncomplete()) {
      return error('모든 필수 항목에 응답해 주세요.');
    }

    if (hasErrors) {
      return error('지원자 정보를 확인해주세요.');
    }

    if (!personalDataCollection) {
      return error('개인정보 수집 및 이용 동의에 체크해주세요.');
    }

    apply({
      body: {
        applicant: {
          ...applicant,
          phone: applicant.phone,
        },
        answers: Object.entries(answers).map(([questionId, answer]) => ({
          questionId,
          replies: [...answer],
        })),
        personalDataCollection,
      } as ApplyRequestBody,
    });
  };

  const handlePersonalDataCollection = (checked: boolean) => {
    setPersonalDataCollection(checked);
  };

  return (
    <C.ContentContainer>
      <S.Form onSubmit={handleSubmit}>
        <InputField
          {...register('name', { validate: { onBlur: validateName.onBlur, onChange: validateName.onChange } })}
          name="name"
          label="이름"
          placeholder="이름을 입력해 주세요."
          maxLength={32}
          required
        />
        <InputField
          {...register('email', { validate: { onBlur: validateEmail.onBlur } })}
          label="이메일"
          placeholder="지원 결과를 안내받을 이메일 주소를 입력해 주세요."
          required
        />
        <InputField
          {...register('phone', {
            validate: {
              onBlur: validatePhoneNumber.onBlur,
              onChange: validatePhoneNumber.onChange,
            },
          })}
          inputMode="numeric"
          label="전화 번호"
          placeholder="번호만 입력해 주세요."
          maxLength={11}
          required
        />

        {questions.map((question) => (
          <CustomQuestion
            key={question.questionId}
            question={question}
            value={answers[question.questionId]}
            isLengthVisible
            onChange={changeHandler[question.type]}
          />
        ))}

        <S.Divider />
        {/* TODO: CheckBoxField를 만들어 보기 */}
        <S.CheckBoxContainer>
          <S.CheckBoxOption>
            <CheckBox
              isChecked={personalDataCollection}
              onToggle={handlePersonalDataCollection}
            />
            <S.CheckBoxLabel required>개인정보 수집 및 이용 동의</S.CheckBoxLabel>
          </S.CheckBoxOption>

          <S.PersonalDataCollectionDescription>
            입력하신 정보는 지원자 식별, 본인 확인, 모집 전형 진행을 위해 사용됩니다.
          </S.PersonalDataCollectionDescription>
        </S.CheckBoxContainer>

        <C.ButtonContainer>
          <Button
            type="submit"
            color="primary"
            size="fillContainer"
            disabled={isClosed}
          >
            제출하기
          </Button>
        </C.ButtonContainer>
      </S.Form>
    </C.ContentContainer>
  );
}
