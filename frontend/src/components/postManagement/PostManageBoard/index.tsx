import { useEffect, useRef, useState } from 'react';
import ReactQuill from 'react-quill-new';
import { RecruitmentInfoState } from '@customTypes/dashboard';

import DateInput from '@components/common/DateInput';
import InputField from '@components/common/InputField';

import usePostManagement from '@hooks/usePostManagement';
import useForm from '@hooks/utils/useForm';
import formatDate from '@utils/formatDate';

import { validateTitle } from '@domain/validations/recruitment';
import TextEditor from '@components/common/TextEditor';
import Button from '@components/common/Button';
import S from './style';

interface PostManageBoardProps {
  postId: string;
}

export default function PostManageBoard({ postId }: PostManageBoardProps) {
  const wrapperRef = useRef<HTMLDivElement>(null);
  const quillRef = useRef<ReactQuill | null>(null);

  const { isLoading, postState, setPostState, modifyPostMutator } = usePostManagement({ postId });
  const startDateText = postState ? formatDate(postState.startDate) : '';
  const endDateText = postState ? formatDate(postState.endDate) : '';
  const [contentText, setContentText] = useState<string | undefined>('');

  const { register, errors } = useForm<RecruitmentInfoState>({ initialValues: postState });

  useEffect(() => {
    setContentText(quillRef.current?.unprivilegedEditor?.getText());
  }, [quillRef]);

  useEffect(() => {
    if (wrapperRef.current && !isLoading) {
      wrapperRef.current.scrollTop = 0;
    }
  }, [isLoading]);

  if (isLoading || !postState) {
    return <div>로딩 중입니다...</div>;
  }

  const isModifyButtonValid = !!(
    postState &&
    postState.startDate &&
    postState.endDate &&
    postState.title.trim() &&
    contentText?.trim() &&
    !Object.values(errors).some((error) => error)
  );

  const handleStartDate = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPostState((prev) => ({
      ...prev,
      startDate: new Date(e.target.value).toISOString(),
    }));
  };

  const handleEndDate = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPostState((prev) => ({
      ...prev,
      endDate: new Date(e.target.value).toISOString(),
    }));
  };

  const handleTitle = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPostState((prev) => ({
      ...prev,
      title: e.target.value,
    }));
  };

  const handlePostingContentChange = (string: string) => {
    setPostState((prev) => ({
      ...prev,
      postingContent: string,
    }));
    setContentText(quillRef.current?.unprivilegedEditor?.getText());
  };

  const handleSubmit = (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();
    modifyPostMutator.mutate();
  };

  return (
    <S.Wrapper ref={wrapperRef}>
      <S.Section>
        <S.SectionTitleContainer>
          <h2>모집일자</h2>
          <span>정해진 날짜가 지나면 자동으로 공고가 마감되며 비활성화 처리됩니다.</span>
        </S.SectionTitleContainer>
        <S.DatePickerContainer>
          <S.DatePickerBox>
            <DateInput
              width="22rem"
              label="시작일"
              min={postState.startDate.split('T')[0]}
              max={postState.endDate.split('T')[0]}
              innerText={startDateText}
              onChange={handleStartDate}
            />
          </S.DatePickerBox>
          <S.DatePickerBox>
            <DateInput
              width="22rem"
              label="종료일"
              min={postState.startDate.split('T')[0]}
              disabled={!startDateText}
              innerText={endDateText}
              onChange={handleEndDate}
            />
          </S.DatePickerBox>
        </S.DatePickerContainer>
      </S.Section>

      <S.RecruitTitleContainer>
        <S.SectionTitleContainer>
          <h2>공고 제목</h2>
        </S.SectionTitleContainer>
        <InputField
          {...register('title', {
            validate: { onBlur: validateTitle, onChange: validateTitle },
            placeholder: '공고 제목을 입력해 주세요',
            maxLength: 32,
            onChange: handleTitle,
          })}
          value={postState.title}
        />
      </S.RecruitTitleContainer>

      <S.RecruitDetailContainer>
        <S.SectionTitleContainer>
          <h2>상세 정보</h2>
        </S.SectionTitleContainer>
        <TextEditor
          quillRef={quillRef}
          value={postState.postingContent}
          onChange={handlePostingContentChange}
        />
      </S.RecruitDetailContainer>

      <S.Section>
        <S.ModifyButtonContainer>
          <Button
            type="button"
            color="primary"
            size="fillContainer"
            disabled={!isModifyButtonValid}
            onClick={handleSubmit}
          >
            수정하기
          </Button>
        </S.ModifyButtonContainer>
      </S.Section>
    </S.Wrapper>
  );
}
