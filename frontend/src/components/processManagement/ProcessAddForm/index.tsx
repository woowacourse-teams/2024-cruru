import React, { FormEvent, useState } from 'react';

import Button from '@components/_common/atoms/Button';
import InputField from '@components/_common/molecules/InputField';
import TextField from '@components/_common/molecules/TextField';

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

    mutate({
      dashboardId: Number(dashboardId),
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
