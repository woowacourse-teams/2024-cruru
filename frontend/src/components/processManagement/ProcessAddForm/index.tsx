import Button from '@components/common/Button';
import InputField from '@components/common/InputField';
import TextField from '@components/common/TextField';
import { processMutaions } from '@hooks/process';
import React, { FormEvent, useState } from 'react';
import { Process } from '@customTypes/process';
import { DASHBOARD_ID } from '@constants/constants';
import C from '../style';

interface ProcessAddFormProps {
  priorOrderIndex: number;
  toggleForm: () => void;
}

export default function ProcessAddForm({ priorOrderIndex, toggleForm }: ProcessAddFormProps) {
  const [formState, setFormState] = useState<Pick<Process, 'name' | 'description'>>({
    name: '',
    description: '',
  });
  const { mutate } = processMutaions.useCreateProcess({ handleSuccess: toggleForm });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setFormState((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    mutate({
      // TODO: 상수를 전역상태로 관리하는 것으로 변경
      dashboardId: DASHBOARD_ID,
      orderIndex: priorOrderIndex + 1,
      ...formState,
    });
  };

  return (
    <C.ProcessForm onSubmit={handleSubmit}>
      <InputField
        label="프로세스 이름"
        placeholder="32자 이내로 입력해주세요."
        value={formState.name}
        onChange={handleChange}
        maxLength={32}
        name="name"
        required
      />

      <TextField
        label="프로세스 설명"
        placeholder="프로세스에 대한 설명을 입력해주세요."
        value={formState.description}
        onChange={handleChange}
        name="description"
      />

      <C.ButtonWrapper>
        <Button
          type="reset"
          color="white"
          onClick={toggleForm}
          size="fillContainer"
        >
          취소
        </Button>
        <Button
          type="submit"
          color="primary"
          size="fillContainer"
        >
          추가
        </Button>
      </C.ButtonWrapper>
    </C.ProcessForm>
  );
}
