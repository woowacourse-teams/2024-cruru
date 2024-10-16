import type { Meta, StoryObj } from '@storybook/react';
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
  },
  tags: ['autodocs'],
  decorators: [
    (Child) => (
      <div
        style={{ width: '500px', height: '36px', display: 'flex', alignItems: 'center', justifyContent: 'flex-end' }}
      >
        <MultiApplicantContextProvider>
          <DropdownProvider>
            <Child />
          </DropdownProvider>
        </MultiApplicantContextProvider>
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
    isToggled: false,
    processes,
    selectedApplicantIds,
  },
};
