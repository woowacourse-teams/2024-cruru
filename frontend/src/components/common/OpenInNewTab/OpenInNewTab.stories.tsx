import type { Meta, StoryObj } from '@storybook/react';
import OpenInNewTab from '.';

const meta = {
  title: 'Common/Button/OpenInNewTab',
  component: OpenInNewTab,
  parameters: {
    docs: {
      description: {
        component: '버튼을 클릭하면 새 탭에서 링크를 엽니다.',
      },
    },
  },

  tags: ['autodocs'],

  argTypes: {
    url: {
      description: '이동할 링크입니다.',
      control: { type: 'text' },
    },
    title: {
      description: '링크에 대한 설명입니다.',
      control: { type: 'text' },
    },
  },
} satisfies Meta<typeof OpenInNewTab>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    url: 'https://www.cruru.kr',
    title: 'CRURU',
  },
};
