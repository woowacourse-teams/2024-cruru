import type { Meta, StoryObj } from '@storybook/react';
import RecruitmentCardStat from '.';

const meta = {
  title: 'Components/Recruitment/RecruitmentCard/RecruitmentCardStat',
  component: RecruitmentCardStat,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: '동아리 대시보드에 노출되는 공고 카드 하단의 현황 정보 컴포넌트입니다.',
      },
    },
  },
  tags: ['autodocs'],
  argTypes: {
    label: { control: 'text' },
    number: { control: 'number' },
    isTotalStats: { control: 'boolean' },
  },
} satisfies Meta<typeof RecruitmentCardStat>;

export default meta;
type Story = StoryObj<typeof meta>;

export const RecruitmentCardStatDefault: Story = {
  args: {
    label: '평가 대상',
    number: 23,
  },
};

export const RecruitmentCardStatTotalStats: Story = {
  args: {
    label: '전체',
    number: 59,
    isTotalStats: true,
  },
};
