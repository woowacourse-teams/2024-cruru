import type { Meta, StoryObj } from '@storybook/react';
import CopyToClipboard from '.';

const meta = {
  title: 'Common/Button/CopyToClipboard',
  component: CopyToClipboard,
  parameters: {
    docs: {
      description: {
        component:
          '버튼을 클릭하면 URL이 클립보드에 복사되며, URL을 클릭하면 새 탭에서 해당 URL로 이동하는 컴포넌트입니다.',
      },
    },
  },

  tags: ['autodocs'],

  argTypes: {
    url: {
      description: '버튼 안에 들어갈 링크입니다.',
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
