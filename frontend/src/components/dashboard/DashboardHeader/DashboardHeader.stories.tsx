import type { Meta, StoryObj } from '@storybook/react';
import { withRouter } from 'storybook-addon-remix-react-router';
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
    postUrl: { control: 'text' },
    startDate: { control: 'date' },
    endDate: { control: 'date' },
  },
  decorators: [
    withRouter,
    (Story) => (
      <div style={{ width: '720px' }}>
        <Story />
      </div>
    ),
  ],
} satisfies Meta<typeof DashboardHeader>;

export default meta;
type Story = StoryObj<typeof meta>;

export const DashboardHeaderDefault: Story = {
  args: {
    title: '우아한테크코스 7기 프론트엔드 모집',
    postUrl: 'https://www.cruru.kr',
    startDate: '2024-07-15T09:00:00Z',
    endDate: '2024-12-15T09:00:00Z',
  },
};
