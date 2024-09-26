import { useState } from 'react';
import Button from '@components/_common/atoms/Button';

import InputField from '@components/_common/molecules/InputField';
import TextField from '@components/_common/molecules/TextField';
import { HiChevronDown, HiChevronUp, HiX } from 'react-icons/hi';
import Spinner from '@components/_common/atoms/Spinner';

import S from './style';

export interface SubmitProps {
  subject: string;
  content: string;
}

interface MessageFormProps {
  recipient: string;
  onSubmit: (formData: SubmitProps) => void;
  onClose: () => void;
  isPending: boolean;
}

export default function MessageForm({ recipient, onSubmit, onClose, isPending }: MessageFormProps) {
  const [subject, setSubject] = useState('');
  const [subjectError, setSubjectError] = useState<string | undefined>(undefined);
  const [content, setContent] = useState('');
  const [contentError, setContentError] = useState<string | undefined>(undefined);
  const [isHidden, setIsHidden] = useState(false);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (subject && content && !subjectError && !contentError) {
      onSubmit({ subject, content });
    }
    if (!subject) setSubjectError('제목을 입력해주세요.');

    if (!content) setContentError('내용을 입력해주세요.');
  };

  const handleSubjectBlur = () => {
    if (subject.length > 500) {
      setSubjectError('제목은 500자를 초과할 수 없습니다.');
    } else if (!subject) {
      setSubjectError('제목을 입력해주세요.');
    } else {
      setSubjectError(undefined);
    }
  };
  const handleContentBlur = () => {
    if (content.length > 2000) {
      setContentError('내용은 2000자를 초과할 수 없습니다.');
    } else if (!content) {
      setContentError('내용을 입력해주세요.');
    } else {
      setContentError(undefined);
    }
  };

  return (
    <S.Container>
      <S.FormHeader>
        <S.Title>{`${recipient}에게 이메일 보내기`}</S.Title>
        <S.ControlIcons>
          {isHidden ? (
            <S.IconButton onClick={() => setIsHidden(false)}>
              <HiChevronUp size={24} />
            </S.IconButton>
          ) : (
            <S.IconButton onClick={() => setIsHidden(true)}>
              <HiChevronDown size={24} />
            </S.IconButton>
          )}
          <S.IconButton onClick={onClose}>
            <HiX size={20} />
          </S.IconButton>
        </S.ControlIcons>
      </S.FormHeader>
      {!isHidden && (
        <S.FormWrapper onSubmit={handleSubmit}>
          <InputField
            label="제목"
            placeholder="이메일 제목을 입력하세요."
            value={subject}
            onChange={(e) => setSubject(e.target.value)}
            maxLength={500}
            onBlur={handleSubjectBlur}
            error={subjectError}
          />

          <TextField
            label="내용"
            placeholder="이메일 내용을 입력하세요."
            value={content}
            onChange={(e) => setContent(e.target.value)}
            maxLength={2000}
            resize={false}
            style={{ height: '30rem' }}
            onBlur={handleContentBlur}
            error={contentError}
          />

          <Button
            type="submit"
            size="fillContainer"
            color="primary"
            style={{ height: '4.4rem' }}
          >
            {isPending ? <Spinner width={40} /> : '이메일 보내기 '}
          </Button>
        </S.FormWrapper>
      )}
    </S.Container>
  );
}
