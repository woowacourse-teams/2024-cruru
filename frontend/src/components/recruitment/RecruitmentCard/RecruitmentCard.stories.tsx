import type { Meta, StoryObj } from '@storybook/react';
import RecruitmentCard from '.';

const meta = {
  title: 'Components/Recruitment/RecruitmentCard',
  component: RecruitmentCard,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: '동아리 대시보드에 노출되는 공고 카드 컴포넌트입니다.',
      },
    },
  },
  tags: ['autodocs'],
  argTypes: {
    dashboardId: { control: 'number' },
    title: { control: 'text' },
    postStats: {
      accept: { control: 'number' },
      fail: { control: 'number' },
      inProgress: { control: 'number' },
      total: { control: 'number' },
    },
    endDate: { control: 'date' },
  },
} satisfies Meta<typeof RecruitmentCard>;

export default meta;
type Story = StoryObj<typeof meta>;

export const RecruitmentCardDefault: Story = {
  args: {
    dashboardId: 1,
    title: '프론트엔드 7기 모집',
    postStats: {
      accept: 42,
      fail: 21,
      inProgress: 591,
      total: 654,
    },
    endDate: '2023-09-15T09:00:00Z',
    onClickTitle: () => console.log('공고 카드의 제목이 클릭되었습니다.'),
    onClickShareButton: () => console.log('공고에 대한 URL 공유 카드가 클릭되었습니다.'),
  },
};
