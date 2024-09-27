import type { Meta, StoryObj } from '@storybook/react';
import ApplicantCard from '.';

const meta = {
  title: 'Organisms/Dashboard/ApplicantCard',
  component: ApplicantCard,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: 'ApplicantCard 컴포넌트는 지원자의 간단한 정보를 담고있는 버튼 컴포넌트입니다.',
      },
    },
  },
  argTypes: {
    name: { control: 'text', description: '지원자 카드의 이름 부분을 나타냅니다.' },
    createdAt: { control: 'date' },
  },
  tags: ['autodocs'],
  decorators: [
    (Child) => (
      <div style={{ width: '350px' }}>
        <Child />
      </div>
    ),
  ],
} satisfies Meta<typeof ApplicantCard>;

export default meta;
type Story = StoryObj<typeof meta>;

export const ApplicantCardDefault: Story = {
  args: {
    name: '홍길동',
    createdAt: '2023-07-15T09:00:00Z',
    evaluationCount: 2,
    averageScore: 3.86,
    isRejected: false,
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
    onCardClick: () => console.log('지원자 카드가 클릭되었습니다.'),
  },
};

export const RejectedApplicantCard: Story = {
  args: {
    name: '홍길동',
    createdAt: '2023-07-15T09:00:00Z',
    evaluationCount: 2,
    averageScore: 2.23,
    isRejected: true,
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
    onCardClick: () => console.log('지원자 카드가 클릭되었습니다.'),
  },
};
