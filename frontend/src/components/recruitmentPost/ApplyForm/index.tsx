import Button from '@components/common/Button';
import InputField from '@components/common/InputField';
import CustomQuestion from '@components/recruitmentPost/CustomQuestion';
import { FormEventHandler, useState } from 'react';

import { validateEmail, validateName, validatePhoneNumber } from '@domain/validations/apply';

import { ApplicantData, ApplyRequestBody, Question } from '@customTypes/apply';
import { applyMutations, applyQueries } from '@hooks/apply';
import useForm from '@hooks/utils/useForm';
import { formatPhoneNumber } from '@utils/formatPhoneNumber';
import { useParams } from 'react-router-dom';

import CheckBox from '@components/common/CheckBox';
import C from '../style';
import S from './style';
import { useAnswers } from './useAnswers';

interface ApplyFormProps {
  questions: Question[];
  isClosed: boolean;
}

export default function ApplyForm({ questions, isClosed }: ApplyFormProps) {
  const { postId } = useParams<{ postId: string }>() as { postId: string };

  const { data: recruitmentPost } = applyQueries.useGetRecruitmentPost({ postId: postId ?? '' });
  const { mutate: apply } = applyMutations.useApply(postId, recruitmentPost?.title ?? '');

  const {
    formData: applicant,
    register,
    errors,
  } = useForm<ApplicantData>({
    initialValues: { name: '', email: '', phone: '' },
  });

  const { answers, changeHandler } = useAnswers(questions);
  const [personalDataCollection, setPersonalDataCollection] = useState(false);

  const handleSubmit: FormEventHandler<HTMLFormElement> = (e) => {
    e.preventDefault();

    if (Object.values(answers).some((answer) => answer.length === 0)) {
      if (!window.confirm('작성하지 않은 질문이 있습니다. 제출하시겠습니까?')) {
        return;
      }
    }

    if (Object.values(errors).some((error) => error)) {
      window.alert('지원자 정보를 확인해주세요.');
      return;
    }

    if (!personalDataCollection) {
      window.alert('개인정보 수집 및 이용 동의에 체크해주세요.');
      return;
    }

    apply({
      body: {
        applicant: {
          ...applicant,
          phone: applicant.phone.replace(/-/g, ''),
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
            formatter: formatPhoneNumber,
          })}
          inputMode="numeric"
          label="전화 번호"
          placeholder="번호만 입력해 주세요."
          maxLength={13}
          required
        />

        {questions.map((question) => (
          <CustomQuestion
            key={question.questionId}
            question={question}
            value={answers[question.questionId]}
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
