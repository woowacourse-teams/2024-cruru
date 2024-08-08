import type { Meta, StoryObj } from '@storybook/react';
import QuestionController from './index';

const meta: Meta<typeof QuestionController> = {
  title: 'Components/Dashboard/Create/Apply/QuestionController',
  component: QuestionController,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component:
          'QuestionController 컴포넌트는 "지원서 작성" 단계에서 사전 질문 항목의 순서 변경/삭제를 담당하는 컴포넌트입니다.',
      },
    },
  },
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<typeof QuestionController>;

export const Default: Story = {};
