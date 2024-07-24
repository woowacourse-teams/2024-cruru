import type { Meta, StoryObj } from '@storybook/react';
import ApplicantCard from '.';

const meta = {
  title: 'Components/ApplicantCard',
  component: ApplicantCard,
  parameters: {
    layout: 'centered',
  },
  argTypes: {
    name: { control: 'text' },
    createdAt: { control: 'date' },
  },
} satisfies Meta<typeof ApplicantCard>;

export default meta;
type Story = StoryObj<typeof meta>;

export const ApplicantCardDefault: Story = {
  args: {
    name: '홍길동',
    createdAt: '2023-07-15T09:00:00Z',
    popOverMenuItems: [
      {
        id: 1,
        name: '단계 이동',
        onClick: () => console.log('지원자에 대한 "단계 이동" 클릭'),
      },
      {
        id: 2,
        name: '불합격',
        isHighlight: true,
        onClick: () => console.log('지원자에 대한 "불합격" 클릭'),
      },
    ],
  },
};
