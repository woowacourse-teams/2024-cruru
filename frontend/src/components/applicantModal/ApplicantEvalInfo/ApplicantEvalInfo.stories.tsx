import type { Meta, StoryObj } from '@storybook/react';
import ApplicantEvalInfo from './index';

const meta: Meta<typeof ApplicantEvalInfo> = {
  title: 'Components/ApplicantModal/ApplicantEvalInfo',
  component: ApplicantEvalInfo,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: '지원자 평가 목록 및 입력폼이 포함된 컴포넌트입니다.',
      },
    },
  },
  tags: ['autodocs'],
  decorators: [
    (Story) => (
      <div style={{ padding: '20px', minWidth: '400px' }}>
        <Story />
      </div>
    ),
  ],
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    evaluationResults: [
      {
        evaluationId: 1,
        evaluatorName: '아르',
        score: '2',
        content: '인재상과 맞지 않습니다.',
        createdAt: '2024-07-16T05:46:08.328593',
      },
      {
        evaluationId: 2,
        evaluatorName: '러기',
        score: '3',
        content: '맞춤법 틀림',
        createdAt: '2024-07-17T05:46:08.328593',
      },
      {
        evaluationId: 3,
        evaluatorName: '렛서',
        score: '5',
        content: '전 마음에 들어요',
        createdAt: '2024-07-18T05:46:08.328593',
      },
    ],
  },
};

Default.parameters = {
  docs: {
    description: {
      story: 'ApplicantEvalInfo 컴포넌트의 기본 상태입니다.',
    },
  },
};
