import type { Meta, StoryObj } from '@storybook/react';
import RecruitmentStatusFlag from '.';

const meta = {
  title: 'Organisms/Recruitment/RecruitmentStatusFlag',
  component: RecruitmentStatusFlag,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: '현재 공고의 모집 진행 상태를 표시하는 플래그 컴포넌트입니다.',
      },
    },
  },
  tags: ['autodocs'],
  argTypes: {
    startDate: { control: 'date' },
    endDate: { control: 'date' },
  },
} satisfies Meta<typeof RecruitmentStatusFlag>;

export default meta;
type Story = StoryObj<typeof meta>;

export const RecruitmentStatusFlagDefault: Story = {
  args: {
    startDate: '2024-07-15T09:00:00Z',
    endDate: '2024-12-15T09:00:00Z',
  },
};
