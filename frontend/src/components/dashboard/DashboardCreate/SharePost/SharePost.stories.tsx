import type { Meta, StoryObj } from '@storybook/react';
import SharePost from '.';

const meta = {
  title: 'Components/Dashboard/Create/Finish/SharePost',
  component: SharePost,
  parameters: {
    docs: {
      description: {
        component: '게시된 공고로 이동 혹은 공고의 링크를 복사할 수 있는 컴포넌트입니다.',
      },
    },
  },

  tags: ['autodocs'],

  argTypes: {
    url: {
      description: '게시된 공고의 링크입니다.',
      control: { type: 'text' },
    },
  },
} satisfies Meta<typeof SharePost>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    url: 'https://www.cruru.kr',
  },
};
