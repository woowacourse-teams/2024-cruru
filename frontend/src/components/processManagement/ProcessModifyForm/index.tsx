import Button from '@components/common/Button';
import InputField from '@components/common/InputField';
import TextField from '@components/common/TextField';
import { Process } from '@customTypes/process';
import React, { FormEvent, useState } from 'react';
import { processMutaions } from '@hooks/process';
import C from '../style';

interface ProcessModifyFormProps {
  process: Process;
  isDeletable?: boolean;
}

export default function ProcessModifyForm({ process, isDeletable = false }: ProcessModifyFormProps) {
  const { mutate: modifyMutate } = processMutaions.useModifyProcess();
  const { mutate: deleteMutate } = processMutaions.useDeleteProcess();

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
    modifyMutate({ ...formState, processId: process.processId });
  };

  const deleteProcess = () => {
    if (window.confirm('정말로 삭제하시겠습니까?\n삭제하면 지원자들의 정보도 함께 삭제되며 복구할 수 없습니다.')) {
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
      />

      <C.ButtonWrapper>
        <Button
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
