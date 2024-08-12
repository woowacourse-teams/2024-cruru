import type { Meta, StoryObj } from '@storybook/react';
import ProcessAddButton from '.';

const meta = {
  title: 'Common/ProcessManagement/ProcessAddButton',
  component: ProcessAddButton,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: '프로세스 추가 버튼 컴포넌트입니다. 클릭하면 프로세스 추가 폼이 토글됩니다.',
      },
    },
  },

  tags: ['autodocs'],
} satisfies Meta<typeof ProcessAddButton>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    postId: 1,
    priorOrderIndex: 0,
  },
};
