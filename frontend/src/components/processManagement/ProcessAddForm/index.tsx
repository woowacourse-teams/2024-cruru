import React, { FormEvent, useState } from 'react';

import Button from '@components/common/Button';
import InputField from '@components/common/InputField';
import TextField from '@components/common/TextField';

import { processMutations } from '@hooks/process';
import { useClickOutside } from '@hooks/utils/useClickOutside';

import { Process } from '@customTypes/process';
import { PROCESS } from '@constants/constants';
import C from '../style';

interface ProcessAddFormProps {
  dashboardId: string;
  applyFormId: string;
  priorOrderIndex: number;
  toggleForm: () => void;
}

export default function ProcessAddForm({ dashboardId, applyFormId, priorOrderIndex, toggleForm }: ProcessAddFormProps) {
  const formRef = useClickOutside<HTMLFormElement>(toggleForm);

  const { mutate } = processMutations.useCreateProcess({ handleSuccess: toggleForm, dashboardId, applyFormId });

  const [formState, setFormState] = useState<Pick<Process, 'name' | 'description'>>({
    name: '',
    description: '',
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setFormState((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    // 현재 스펙상, API와 FE 측의 dashboardId 정의가 서로 다릅니다.
    // API 측에서의 dashboardId는 개별 공고가 가진 ID값이지만, FE측에서는 postId가 이 ID값을 의미합니다.
    // 따라서 API로 데이터를 전송할 때, dashboardId 필드에는 Number로 변환된 postId를 보내야 합니다.
    // 2024-08-12 by 아르
    mutate({
      dashboardId: Number(applyFormId),
      orderIndex: priorOrderIndex + 1,
      ...formState,
    });
  };

  return (
    <C.ProcessForm
      ref={formRef}
      onSubmit={handleSubmit}
    >
      <InputField
        label={PROCESS.inputField.name.label}
        placeholder={PROCESS.inputField.name.placeholder}
        maxLength={PROCESS.inputField.name.maxLength}
        value={formState.name}
        onChange={handleChange}
        name="name"
        required
      />

      <TextField
        label={PROCESS.inputField.description.label}
        placeholder={PROCESS.inputField.description.placeholder}
        value={formState.description}
        onChange={handleChange}
        name="description"
        required
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
