import type { Meta, StoryObj } from '@storybook/react';
import ProcessModifyForm from '.';

const meta = {
  title: 'Components/ProcessManagement/ProcessModifyForm',
  component: ProcessModifyForm,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: '프로세스 추가 폼 컴포넌트입니다. 프로세스 이름과 설명을 입력받습니다.',
      },
    },
  },

  tags: ['autodocs'],

  argTypes: {
    process: {
      description: '수정할 프로세스 정보입니다.',
    },
  },
} satisfies Meta<typeof ProcessModifyForm>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    dashboardId: '1',
    applyFormId: '1',
    process: {
      processId: 1,
      name: '서류 접수',
      description: '',
      orderIndex: 0,
      applicants: [],
    },
    isDeletable: true,
  },
};
