import type { Meta, StoryObj } from '@storybook/react';
import QuestionBuilder from './index';

const meta: Meta<typeof QuestionBuilder> = {
  title: 'Organisms/Dashboard/DashboardCreate/Apply/QuestionBuilder',
  component: QuestionBuilder,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component:
          'QuestionBuilder 컴포넌트는 "지원서 작성" 단계에서 사전 질문 항목을 생성하거나 수정하는 컴포넌트입니다.',
      },
    },
  },
  tags: ['autodocs'],
  decorators: [
    (Child) => (
      <div
        style={{
          minWidth: '600px',
        }}
      >
        <Child />
      </div>
    ),
  ],
};

export default meta;
type Story = StoryObj<typeof QuestionBuilder>;

export const Default: Story = {};
