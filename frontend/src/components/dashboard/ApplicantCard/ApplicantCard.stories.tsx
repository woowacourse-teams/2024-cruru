import type { Meta, StoryObj } from '@storybook/react';
import { DropdownProvider } from '@contexts/DropdownContext';
import { DropdownItemType } from '@components/_common/molecules/DropdownItemRenderer';
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
        <DropdownProvider>
          <Child />
        </DropdownProvider>
      </div>
    ),
  ],
} satisfies Meta<typeof ApplicantCard>;

export default meta;
type Story = StoryObj<typeof meta>;

const processList = [
  {
    processName: '지원서',
    processId: 1,
  },
  {
    processName: '프로세스 1',
    processId: 4,
  },
  {
    processName: '프로세스 2',
    processId: 2,
  },
  {
    processName: '프로세스 3',
    processId: 5,
  },
  {
    processName: '최종 합격',
    processId: 6,
  },
];

const popOverMenuItems: DropdownItemType[] = [
  {
    type: 'subTrigger',
    id: 'moveProcess',
    name: '단계 이동',
    items: processList.map(({ processName, processId }) => ({
      type: 'clickable',
      id: processId,
      name: processName,
      onClick: ({ targetProcessId }) => {
        alert(`이동할 Process의 Id는 ${targetProcessId}입니다.`);
      },
    })),
  },
  {
    type: 'clickable',
    id: 'emailButton',
    name: '이메일 보내기',
    hasSeparate: true,
    onClick: () => alert('지원자에 대한 이메일 보내기 클릭'),
  },
  {
    type: 'clickable',
    id: 'rejectButton',
    name: '불합격 처리',
    isHighlight: true,
    hasSeparate: true,
    onClick: () => alert('지원자에 대한 불합격 처리 클릭'),
  },
];

export const ApplicantCardDefault: Story = {
  args: {
    name: '홍길동',
    createdAt: '2023-07-15T09:00:00Z',
    evaluationCount: 2,
    averageScore: 3.86,
    isRejected: false,
    popOverMenuItems,
    isSelectMode: false,
    isSelected: false,
    onCardClick: () => console.log('지원자 카드가 클릭되었습니다.'),
    onSelectApplicant: () => console.log('지원자 선택 체크박스가 클릭되었습니다.'),
  },
};

export const RejectedApplicantCard: Story = {
  args: {
    name: '홍길동',
    createdAt: '2023-07-15T09:00:00Z',
    evaluationCount: 2,
    averageScore: 2.23,
    isRejected: true,
    popOverMenuItems,
    isSelectMode: false,
    isSelected: false,
    onCardClick: () => console.log('지원자 카드가 클릭되었습니다.'),
    onSelectApplicant: () => console.log('지원자 선택 체크박스가 클릭되었습니다.'),
  },
};
