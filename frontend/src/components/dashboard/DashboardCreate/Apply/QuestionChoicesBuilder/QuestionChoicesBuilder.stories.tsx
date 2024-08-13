/* eslint-disable react-hooks/rules-of-hooks */
import { useState } from 'react';
import type { Meta, StoryObj } from '@storybook/react';
import { QuestionOptionValue } from '@customTypes/dashboard';
import QuestionChoicesBuilder from './index';

const meta: Meta<typeof QuestionChoicesBuilder> = {
  title: 'Components/Dashboard/Create/Apply/QuestionChoicesBuilder',
  component: QuestionChoicesBuilder,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component:
          'QuestionChoicesBuilder 컴포넌트는 "지원서 작성" 단계에서 "객관식/복수 선택" 질문의 옵션 항목을 편집하는 컴포넌트입니다.',
      },
    },
  },
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<typeof QuestionChoicesBuilder>;

export const Default: Story = {
  render: (args) => {
    const [choices, setChoices] = useState<QuestionOptionValue[]>([
      { choice: '첫 번째 옵션입니다.' },
      { choice: '두 번째 옵션입니다.' },
      { choice: '세 번째 옵션입니다.' },
    ]);

    const onUpdate = (newChoices: QuestionOptionValue[]) => {
      setChoices(newChoices);
    };

    return (
      <div style={{ width: '600px' }}>
        <QuestionChoicesBuilder
          {...args}
          choices={choices}
          onUpdate={onUpdate}
        />
      </div>
    );
  },
};
