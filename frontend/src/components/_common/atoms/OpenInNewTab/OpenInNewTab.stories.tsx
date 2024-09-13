import { withRouter } from 'storybook-addon-remix-react-router';
import type { Meta, StoryObj } from '@storybook/react';
import OpenInNewTab from '.';

const meta = {
  title: 'Common/Atoms/OpenInNewTab',
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

  decorators: [withRouter], // 내부에서 React-Router의 Link를 사용하고 있으므로 넣어줘야 합니다.
} satisfies Meta<typeof OpenInNewTab>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    url: 'https://www.cruru.kr',
    title: 'CRURU',
  },
};
