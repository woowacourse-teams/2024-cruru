import Button from '@components/_common/atoms/Button';
import { createContext, ReactNode, useContext, useMemo, useState } from 'react';

import Spinner from '@components/_common/atoms/Spinner';
import InputField from '@components/_common/molecules/InputField';
import TextField from '@components/_common/molecules/TextField';
import { HiChevronDown, HiChevronUp, HiX } from 'react-icons/hi';

import S from './style';

export interface SubmitProps {
  subject: string;
  content: string;
}

interface MessageFormContextValue {
  isHidden: boolean;
  toggleHidden: () => void;
}

const MessageFormContext = createContext<MessageFormContextValue | undefined>(undefined);

function useMessageFormContext() {
  const context = useContext(MessageFormContext);
  if (!context) throw new Error('MessageForm.Header must be used within a MessageForm');
  return context;
}

export default function MessageForm({ children }: { children: React.ReactNode }) {
  const [isHidden, setIsHidden] = useState(false);

  const toggleHidden = () => setIsHidden(!isHidden);
  const value = useMemo(() => ({ isHidden, toggleHidden }), [isHidden]);

  return <MessageFormContext.Provider value={value}>{children}</MessageFormContext.Provider>;
}

MessageForm.FloatingBody = function Body({ children }: { children: ReactNode }) {
  return <S.Container>{children}</S.Container>;
};

MessageForm.Header = function Header({ recipient, onClose }: { recipient: string; onClose: () => void }) {
  const { isHidden, toggleHidden } = useMessageFormContext();

  return (
    <S.FormHeader>
      <S.Title>{`${recipient}에게 이메일 보내기`}</S.Title>
      <S.ControlIcons>
        {isHidden ? (
          <S.IconButton onClick={toggleHidden}>
            <HiChevronUp size={24} />
          </S.IconButton>
        ) : (
          <S.IconButton onClick={toggleHidden}>
            <HiChevronDown size={24} />
          </S.IconButton>
        )}
        <S.IconButton onClick={onClose}>
          <HiX size={20} />
        </S.IconButton>
      </S.ControlIcons>
    </S.FormHeader>
  );
};

MessageForm.Form = function Form({
  onSubmit,
  isPending,
}: {
  onSubmit: (formData: SubmitProps) => void;
  isPending: boolean;
}) {
  const { isHidden } = useMessageFormContext();

  const [subject, setSubject] = useState('');
  const [subjectError, setSubjectError] = useState<string | undefined>(undefined);
  const [content, setContent] = useState('');
  const [contentError, setContentError] = useState<string | undefined>(undefined);

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

  if (isHidden) return null;

  return (
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
  );
};
