import { useRef, useState } from 'react';
import Button from '@components/common/Button';
import ChevronButton from '@components/common/ChevronButton';
import DateInput from '@components/common/DateInput';
import InputField from '@components/common/InputField';
import TextEditor from '@components/common/TextEditor';
import { RecruitmentInfoState } from '@customTypes/dashboard';
import formatDate from '@utils/formatDate';
import ReactQuill from 'react-quill-new';
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
  const isNextButtonValid = !!(endDate && contentText && startDate && title);

  const handleStartDate = (e: React.ChangeEvent<HTMLInputElement>) => {
    setRecruitmentInfoState((prev) => {
      const temp = { ...prev };
      temp.startDate = new Date(e.target.value).toISOString();
      return temp;
    });
  };

  const handleEndDate = (e: React.ChangeEvent<HTMLInputElement>) => {
    setRecruitmentInfoState((prev) => {
      const temp = { ...prev };
      temp.endDate = new Date(e.target.value).toISOString();
      return temp;
    });
  };

  const handleTitle = (e: React.ChangeEvent<HTMLInputElement>) => {
    setRecruitmentInfoState((prev) => {
      const temp = { ...prev };
      temp.title = e.target.value;
      return temp;
    });
  };

  const handlePostingContentChange = (string: string) => {
    setRecruitmentInfoState((prev) => {
      const temp = { ...prev };
      temp.postingContent = string;
      return temp;
    });
  };

  const handlePostingContentBlur = () => {
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
          value={recruitmentInfoState.title}
          onChange={handleTitle}
        />
      </S.RecruitTitleContainer>

      <S.RecruitDetailContainer>
        <S.Title>상세 정보</S.Title>
        <TextEditor
          quillRef={quillRef}
          value={recruitmentInfoState.postingContent}
          onChange={handlePostingContentChange}
          onBlur={handlePostingContentBlur}
        />
      </S.RecruitDetailContainer>

      <S.NextButtonContainer>
        <Button
          disabled={!isNextButtonValid}
          onClick={nextStep}
          size="sm"
          color="white"
        >
          <S.ButtonContent>
            다음
            <ChevronButton
              direction="right"
              size="sm"
            />
          </S.ButtonContent>
        </Button>
      </S.NextButtonContainer>
    </S.Container>
  );
}
