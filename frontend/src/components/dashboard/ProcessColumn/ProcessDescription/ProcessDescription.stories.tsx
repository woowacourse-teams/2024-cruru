import type { Meta, StoryObj } from '@storybook/react';
import ProcessDescription from './index';

const meta: Meta<typeof ProcessDescription> = {
  title: 'Organisms/ProcessColumn/ProcessDescription',
  component: ProcessDescription,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: '프로세스의 설명을 렌더링합니다. 더보기 버튼을 클릭하면 설명이 전체로 펼쳐집니다.',
      },
    },
  },
  tags: ['autodocs'],
  decorators: [
    (Child) => (
      <div
        style={{
          width: '25rem',
        }}
      >
        <Child />
      </div>
    ),
  ],
  args: {
    description: '프로세스 설명입니다.',
  },
};

export default meta;
type Story = StoryObj<typeof ProcessDescription>;

export const Default: Story = {};
