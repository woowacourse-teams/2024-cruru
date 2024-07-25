import type { Meta, StoryObj } from '@storybook/react';
import QuestionSection from '.';

const meta: Meta<typeof QuestionSection> = {
  title: 'Components/ApplicantModal/ApplicantDetailInfo/QuestionSection',
  component: QuestionSection,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: '여러 개의 QuestionBox를 포함하는 섹션 컴포넌트입니다.',
      },
    },
  },
  args: {
    applicantId: 1,
  },
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
Default.parameters = {
  docs: {
    description: {
      story: 'QuestionSection 컴포넌트의 기본 상태입니다.',
    },
  },
};
