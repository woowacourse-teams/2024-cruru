import type { Meta, StoryObj } from '@storybook/react';
import EvaluationForm from './index';

const meta: Meta<typeof EvaluationForm> = {
  title: 'Organisms/ApplicantModal/ApplicantEvalInfo/EvaluationForm',
  component: EvaluationForm,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: '모달의 지원자에 대한 평가 등록폼 컴포넌트입니다.',
      },
    },
  },
  argTypes: {},
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
    processId: 1,
    applicantId: 1,
    onClose: () => {},
  },
};

Default.parameters = {
  docs: {},
};
