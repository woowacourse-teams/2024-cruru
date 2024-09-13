import type { Meta, StoryObj } from '@storybook/react';
import EvaluationAddButton from '.';

const meta = {
  title: 'Organisms/ApplicantModal/ApplicantEvalInfo/EvaluationAddButton',
  component: EvaluationAddButton,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: '모달의 지원자에 대한 개별 평가 등록 버튼입니다.',
      },
    },
  },

  tags: ['autodocs'],
  decorators: [
    (Story) => (
      <div style={{ padding: '24px', minWidth: '400px', minHeight: '40px' }}>
        <Story />
      </div>
    ),
  ],
} satisfies Meta<typeof EvaluationAddButton>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    onClick: () => {},
  },
};
