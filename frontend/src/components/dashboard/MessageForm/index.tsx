import { useState } from 'react';
import Button from '@components/_common/atoms/Button';

import InputField from '@components/_common/molecules/InputField';
import TextField from '@components/_common/molecules/TextField';
import S from './style';

interface MessageFormProps {
  recipient: string;
  onSubmit: (formData: { subject: string; content: string }) => void;
}

export default function MessageForm({ recipient, onSubmit }: MessageFormProps) {
  const [subject, setSubject] = useState('');
  const [subjectError, setSubjectError] = useState<string | undefined>(undefined);
  const [content, setContent] = useState('');
  const [contentError, setContentError] = useState<string | undefined>(undefined);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (subject && !subjectError && content && !contentError) {
      onSubmit({ subject, content });
    }
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
    <S.FormWrapper onSubmit={handleSubmit}>
      <S.Title>{`${recipient}에게 이메일 보내기`}</S.Title>

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
        이메일 보내기
      </Button>
    </S.FormWrapper>
  );
}
