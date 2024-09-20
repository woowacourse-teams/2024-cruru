import { useEffect, useRef, useState } from 'react';
import ReactQuill from 'react-quill-new';

import { RecruitmentInfoState } from '@customTypes/dashboard';
import formatDate from '@utils/formatDate';
import useForm from '@hooks/utils/useForm';

import { HiChevronRight } from 'react-icons/hi';
import Button from '@components/_common/atoms/Button';
import DateInput from '@components/_common/atoms/DateInput';
import InputField from '@components/_common/molecules/InputField';
import TextEditor from '@components/_common/atoms/TextEditor';

import { validateTitle } from '@domain/validations/recruitment';
import S from './style';

interface RecruitmentProps {
  recruitmentInfoState: RecruitmentInfoState;
  setRecruitmentInfoState: React.Dispatch<React.SetStateAction<RecruitmentInfoState>>;
  nextStep: () => void;
}

export default function Recruitment({ recruitmentInfoState, setRecruitmentInfoState, nextStep }: RecruitmentProps) {
  const quillRef = useRef<ReactQuill | null>(null);
  const [contentText, setContentText] = useState<string | undefined>('');

  const { endDate, startDate, title } = recruitmentInfoState;
  const today = new Date().toISOString().split('T')[0];
  const startDateText = startDate ? formatDate(startDate) : '';
  const endDateText = endDate ? formatDate(endDate) : '';

  const { register, errors } = useForm<RecruitmentInfoState>({
    initialValues: { title: '', startDate: '', endDate: '', postingContent: '' },
  });

  useEffect(() => {
    setContentText(quillRef.current?.unprivilegedEditor?.getText());
  }, [quillRef]);

  const isNextButtonValid = !!(
    endDate &&
    contentText?.trim() &&
    startDate &&
    title.trim() &&
    !Object.values(errors).some((error) => error)
  );

  const handleStartDate = (e: React.ChangeEvent<HTMLInputElement>) => {
    setRecruitmentInfoState((prev) => ({
      ...prev,
      startDate: e.target.value ? new Date(e.target.value).toISOString() : '',
    }));
  };

  const handleEndDate = (e: React.ChangeEvent<HTMLInputElement>) => {
    setRecruitmentInfoState((prev) => ({
      ...prev,
      endDate: e.target.value ? new Date(e.target.value).toISOString() : '',
    }));
  };

  const handleTitle = (e: React.ChangeEvent<HTMLInputElement>) => {
    setRecruitmentInfoState((prev) => ({
      ...prev,
      title: e.target.value,
    }));
  };

  const handlePostingContentChange = (string: string) => {
    setRecruitmentInfoState((prev) => ({
      ...prev,
      postingContent: string,
    }));
    setContentText(quillRef.current?.unprivilegedEditor?.getText());
  };

  return (
    <S.Container>
      <S.DateContainer>
        <S.Title>모집일자</S.Title>
        <S.Description>정해진 날짜가 지나면 자동으로 공고가 마감되며 비활성화 처리됩니다.</S.Description>
        <S.DatePickerContainer>
          <S.DatePickerBox>
            <DateInput
              width="22rem"
              label="시작일"
              min={today}
              max={recruitmentInfoState.endDate.split('T')[0]}
              innerText={startDateText}
              onChange={handleStartDate}
            />
          </S.DatePickerBox>
          <S.DatePickerBox>
            <DateInput
              width="22rem"
              label="종료일"
              min={recruitmentInfoState.startDate.split('T')[0]}
              disabled={!startDateText}
              innerText={endDateText}
              onChange={handleEndDate}
            />
          </S.DatePickerBox>
        </S.DatePickerContainer>
      </S.DateContainer>

      <S.RecruitTitleContainer>
        <S.Title>공고 제목</S.Title>
        <InputField
          {...register('title', {
            validate: { onBlur: validateTitle, onChange: validateTitle },
            value: recruitmentInfoState.title,
            placeholder: '공고 제목을 입력해 주세요',
            maxLength: 32,
            onChange: handleTitle,
          })}
        />
      </S.RecruitTitleContainer>

      <S.RecruitDetailContainer>
        <S.Title>상세 정보</S.Title>
        <TextEditor
          quillRef={quillRef}
          value={recruitmentInfoState.postingContent}
          onChange={handlePostingContentChange}
        />
      </S.RecruitDetailContainer>

      <S.NextButtonContainer>
        <Button
          disabled={!isNextButtonValid}
          onClick={nextStep}
          size="sm"
          color="white"
          type="button"
        >
          <S.ButtonContent>
            다음
            <HiChevronRight size={24} />
          </S.ButtonContent>
        </Button>
      </S.NextButtonContainer>
    </S.Container>
  );
}
