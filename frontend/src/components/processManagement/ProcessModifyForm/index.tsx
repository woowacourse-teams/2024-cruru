import React, { FormEvent, useState } from 'react';

import Button from '@components/common/Button';
import InputField from '@components/common/InputField';
import TextField from '@components/common/TextField';

import { Process } from '@customTypes/process';
import { processMutations } from '@hooks/process';
import C from '../style';

interface ProcessModifyFormProps {
  dashboardId: string;
  applyFormId: string;
  process: Process;
  isDeletable?: boolean;
}

export default function ProcessModifyForm({
  dashboardId,
  applyFormId,
  process,
  isDeletable = false,
}: ProcessModifyFormProps) {
  const { mutate: modifyMutate } = processMutations.useModifyProcess({ dashboardId, applyFormId });
  const { mutate: deleteMutate } = processMutations.useDeleteProcess({ dashboardId, applyFormId });

  const [formState, setFormState] = useState<Pick<Process, 'name' | 'description'>>({
    name: process.name ?? '',
    description: process.description ?? '',
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setFormState((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const modifyProcess = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (process.description !== formState.description || process.name !== formState.name) {
      modifyMutate({ ...formState, processId: process.processId });
    }
  };

  const deleteProcess = () => {
    if (window.confirm('정말로 삭제하시겠습니까?\n')) {
      deleteMutate(process.processId);
    }
  };

  return (
    <C.ProcessForm onSubmit={modifyProcess}>
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
        required
      />

      <C.ButtonWrapper>
        <Button
          disabled={process.name === formState.name && process.description === formState.description}
          type="submit"
          color="white"
          size="fillContainer"
        >
          수정
        </Button>
        {isDeletable && (
          <Button
            type="button"
            color="error"
            size="fillContainer"
            onClick={deleteProcess}
          >
            삭제
          </Button>
        )}
      </C.ButtonWrapper>
    </C.ProcessForm>
  );
}
