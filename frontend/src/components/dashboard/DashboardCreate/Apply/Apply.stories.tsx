/* eslint-disable react-hooks/rules-of-hooks */
import type { Meta, StoryObj } from '@storybook/react';
import { useState } from '@storybook/preview-api';
import { Question } from '@customTypes/dashboard';
import Apply from './index';

const meta: Meta<typeof Apply> = {
  title: 'Components/Dashboard/Create/Apply',
  component: Apply,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component:
          'Apply 컴포넌트는 공고 작성시 "지원서 작성" 단계의 사전 질문 생성 및 관리를 담당하는 컴포넌트입니다.',
      },
    },
  },
  args: {
    applyState: [
      {
        type: 'SHORT_ANSWER',
        question: '1. 프로그래밍 교육 경험',
        choices: [],
        required: true,
      },
      {
        type: 'LONG_ANSWER',
        question: '2. 효과적인 학습 방식과 경험',
        choices: [],
        required: true,
      },
      {
        type: 'MULTIPLE_CHOICE',
        question: '3. 복수 선택 질문',
        choices: [
          {
            choice: '옵션 1',
            orderIndex: 0,
          },
          {
            choice: '옵션 3',
            orderIndex: 2,
          },
          {
            choice: '옵션 2',
            orderIndex: 1,
          },
        ],
        required: false,
      },
      {
        type: 'SINGLE_CHOICE',
        question: '4. 8/6일 오리엔테이션에 참여할 수 있습니다.',
        choices: [
          {
            choice: '네',
            orderIndex: 0,
          },
          {
            choice: '아니요',
            orderIndex: 1,
          },
        ],
        required: true,
      },
    ],
  },
  tags: ['autodocs'],
  decorators: [
    (Child) => (
      <div
        style={{
          minWidth: '700px',
        }}
      >
        <Child />
      </div>
    ),
  ],
};

export default meta;
type Story = StoryObj<typeof Apply>;

export const Default: Story = {
  render: (args) => {
    const [applyState, setApplyState] = useState(args.applyState);

    const DEFAULT_QUESTION: Question = {
      type: 'SHORT_ANSWER',
      question: '',
      choices: [],
      required: true,
    };

    const addQuestion = () => {
      setApplyState((prevState) => [...prevState, DEFAULT_QUESTION]);
    };

    const prevStep = () => {
      alert('이전 단계로 이동합니다.');
    };

    const nextStep = () => {
      alert('다음 단계로 이동합니다.');
    };

    return (
      <div style={{ width: '700px' }}>
        <Apply
          {...args}
          applyState={applyState}
          addQuestion={addQuestion}
          prevStep={prevStep}
          nextStep={nextStep}
        />
      </div>
    );
  },
};
