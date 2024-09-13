import type { Meta, StoryObj } from '@storybook/react';
import EvaluationCard from './index';

const meta: Meta<typeof EvaluationCard> = {
  title: 'Organisms/ApplicantModal/ApplicantEvalInfo/EvaluationCard',
  component: EvaluationCard,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: '모달의 지원자에 대한 개별 평가 카드 컴포넌트입니다.',
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
    evaluationResult: {
      evaluationId: 1,
      evaluatorName: '평가자 이름',
      score: '4',
      content: '지원자에 대한 평가자의 코멘트가 들어가는 영역입니다.',
      createdDate: '2024-07-16T05:46:08.328593',
    },
  },
};

Default.parameters = {
  docs: {},
};
