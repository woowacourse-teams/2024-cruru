/* eslint-disable no-shadow */
import type { Meta, StoryObj } from '@storybook/react';
import { MultiApplicantContextProvider } from '@contexts/MultiApplicantContext';
import DashboardFunctionTab from '.';

const meta = {
  title: 'Organisms/Dashboard/DashboardFunctionTab',
  component: DashboardFunctionTab,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component:
          'DashboardFunctionTab 컴포넌트는 공고 대시보드 화면에서 지원자 목록에 대한 검색, 정렬, 필터 및 선택 등의 관리 기능을 지원하는 탭 컴포넌트입니다.',
      },
    },
  },
  argTypes: {
    processList: { description: '현재 공고의 모집 심사 단계별 정보를 나타냅니다.' },
    searchedName: { description: '검색하려는 지원자 이름입니다.' },
    onSearchName: {
      description: '지원자 이름을 검색했을 때 사용되는 prop 함수입니다.',
    },
  },
  tags: ['autodocs'],
  decorators: [
    (Child) => (
      <div
        style={{
          width: '72rem',
          height: '10rem',
        }}
      >
        <MultiApplicantContextProvider>
          <Child />
        </MultiApplicantContextProvider>
      </div>
    ),
  ],
} satisfies Meta<typeof DashboardFunctionTab>;

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

export const DashboardFunctionTabDefault: Story = {
  args: {
    processList,
    searchedName: '',
    onSearchName: (name: string) => console.log(`"${name}" 이름으로 검색하셨습니다.`),
  },
};
