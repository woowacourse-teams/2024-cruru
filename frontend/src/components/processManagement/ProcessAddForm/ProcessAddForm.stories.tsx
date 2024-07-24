import type { Meta, StoryObj } from '@storybook/react';
import ProcessAddForm from '.';

const meta = {
  title: 'Components/ProcessManagement/ProcessAddForm',
  component: ProcessAddForm,
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
    priorProcessId: {
      table: {
        disable: true,
      },
    },

    toggleForm: {
      table: {
        disable: true,
      },
    },
  },
} satisfies Meta<typeof ProcessAddForm>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    priorProcessId: 0,
    toggleForm: () => {},
  },
};
