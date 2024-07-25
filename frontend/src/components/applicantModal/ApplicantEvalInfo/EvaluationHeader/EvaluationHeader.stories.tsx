import type { Meta, StoryObj } from '@storybook/react';
import EvaluationHeader from './index';

const meta: Meta<typeof EvaluationHeader> = {
  title: 'Components/ApplicantModal/ApplicantEvalInfo/EvaluationHeader',
  component: EvaluationHeader,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: '헤더 탭과 콘텐츠 영역이 있는 컴포넌트입니다.',
      },
    },
  },
  tags: ['autodocs'],
  argTypes: {
    title: {
      description: '해당 영역에 대한 제목입니다.',
      control: { type: 'text' },
      table: {
        type: { summary: 'string' },
      },
    },
    description: {
      description: '해당 영역에 대한 설명문입니다.',
      control: { type: 'text' },
      table: {
        type: { summary: 'string' },
      },
    },
  },
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    title: '지원자 평가',
    description: '해당 지원자에 대한 평가 내용입니다.',
  },
};

Default.parameters = {
  docs: {
    description: {
      story: 'EvaluationHeader 컴포넌트의 기본 상태입니다.',
    },
  },
};
