import React, { FormEvent, useState } from 'react';
import S from './style';

interface ProcessAddForm {
  processName: string;
  processDescription: string;
  priorProcessId: number;
}

interface ProcessAddFormProps {
  priorProcessId: number;
  toggleForm: () => void;
}

export default function ProcessAddForm({ priorProcessId, toggleForm }: ProcessAddFormProps) {
  const [formState, setFormState] = useState<ProcessAddForm>({
    processName: '',
    processDescription: '',
    priorProcessId,
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormState((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    console.log(formState);
  };

  return (
    <S.Form onSubmit={handleSubmit}>
      <S.Label htmlFor="processName">프로세스 이름</S.Label>
      <S.Input
        value={formState?.processName}
        onChange={handleChange}
        id="processName"
        name="processName"
        type="text"
        placeholder="프로세스 이름"
      />

      <S.Label htmlFor="processDescription">프로세스 설명</S.Label>
      <S.Input
        value={formState?.processDescription}
        onChange={handleChange}
        id="processDescription"
        name="processDescription"
        type="text"
        placeholder="프로세스 설명"
      />

      <S.FormButton
        type="submit"
        color="secondary"
        onClick={toggleForm}
      >
        취소
      </S.FormButton>
      <S.FormButton
        type="submit"
        color="primary"
      >
        추가
      </S.FormButton>
    </S.Form>
  );
}
