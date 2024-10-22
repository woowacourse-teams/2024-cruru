import type { Meta, StoryObj } from '@storybook/react';
import { FloatingEmailFormProvider } from '@contexts/FloatingEmailFormContext';
import { MultiApplicantContextProvider } from '@contexts/MultiApplicantContext';
import { DropdownProvider } from '@contexts/DropdownContext';
import MultiSelectToggle from '.';

const meta = {
  title: 'Organisms/Dashboard/MultiSelectToggle',
  component: MultiSelectToggle,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component:
          'MultiSelectToggle 컴포넌트는 여러 지원자의 선택 여부를 토글하고, 선택시 실행할 공통 작업을 제공하는 컴포넌트입니다.',
      },
    },
  },
  argTypes: {
    isToggled: { control: 'boolean', description: '여러 지원자 선택 토글 여부를 나타냅니다.' },
    selectedApplicantIds: {
      description: '선택된 지원자들의 ID값 목록을 나타냅니다.',
    },
    isRejectedApplicantsTab: {
      description: '현재 공고 대시보드 화면의 위치가 불합격자 관리 탭인지 여부를 나타냅니다.',
    },
  },
  tags: ['autodocs'],
  decorators: [
    (Child) => (
      <div
        style={{
          width: '60rem',
          height: '3.6rem',
          display: 'flex',
          flexDirection: 'row',
          alignItems: 'center',
          justifyContent: 'center',
        }}
      >
        <FloatingEmailFormProvider>
          <MultiApplicantContextProvider>
            <DropdownProvider>
              <Child />
            </DropdownProvider>
          </MultiApplicantContextProvider>
        </FloatingEmailFormProvider>
      </div>
    ),
  ],
} satisfies Meta<typeof MultiSelectToggle>;

export default meta;
type Story = StoryObj<typeof meta>;

const processes = [
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

const selectedApplicantIds = [1, 2, 3];

export const MultiSelectToggleDefault: Story = {
  args: {
    dashboardId: '1',
    applyFormId: '1',
    isToggled: false,
    processes,
    selectedApplicantIds,
    isRejectedApplicantsTab: false,
  },
};
