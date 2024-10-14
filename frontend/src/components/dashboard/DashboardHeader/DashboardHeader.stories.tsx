import type { Meta, StoryObj } from '@storybook/react';
import DashboardHeader from '.';

const meta = {
  title: 'Organisms/Dashboard/DashboardHeader',
  component: DashboardHeader,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: '현재 공고의 대시보드 헤더를 표시하는 컴포넌트입니다.',
      },
    },
  },
  tags: ['autodocs'],
  argTypes: {
    title: { control: 'text' },
    startDate: { control: 'date' },
    endDate: { control: 'date' },
  },
} satisfies Meta<typeof DashboardHeader>;

export default meta;
type Story = StoryObj<typeof meta>;

export const DashboardHeaderDefault: Story = {
  args: {
    title: '우아한테크코스 7기 프론트엔드 모집',
    startDate: '2024-07-15T09:00:00Z',
    endDate: '2024-12-15T09:00:00Z',
  },
};
