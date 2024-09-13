import type { Meta, StoryObj } from '@storybook/react';
import CopyToClipboard from '.';

const meta = {
  title: 'Common/Atoms/CopyToClipboard',
  component: CopyToClipboard,
  parameters: {
    docs: {
      description: {
        component: '버튼을 클릭하면 URL이 클립보드에 복사됩니다.',
      },
    },
  },

  tags: ['autodocs'],

  argTypes: {
    url: {
      description: '버튼을 클릭하면 복사될 URL입니다.',
      control: { type: 'text' },
    },
  },
} satisfies Meta<typeof CopyToClipboard>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    url: 'https://www.cruru.kr',
  },
};
