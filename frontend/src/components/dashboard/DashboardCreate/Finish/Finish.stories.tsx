import type { Meta, StoryObj } from '@storybook/react';
import { reactRouterParameters, withRouter } from 'storybook-addon-remix-react-router';
import Finish from '.';

const meta: Meta<typeof Finish> = {
  title: 'Components/Dashboard/Create/Finish',
  component: Finish,
  decorators: [withRouter],
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component:
          'Finish 컴포넌트는 공고가 게시되었음을 알리고, URL을 복사하거나 공고로 이동할 수 있는 기능을 제공합니다.',
      },
    },
    reactRouter: reactRouterParameters({
      location: {
        pathParams: { dashboardId: '1' },
      },
      routing: { path: '/dashboardId/:dashboardId' },
    }),
  },
  tags: ['autodocs'],
  argTypes: {
    postUrl: {
      description: '게시된 공고의 URL입니다.',
      control: { type: 'text' },
      table: {
        type: { summary: 'string' },
      },
    },
    postId: {
      description: '게시된 공고의 ID입니다.',
      control: { type: 'text' },
      table: {
        type: { summary: 'string' },
      },
    },
  },
};

export default meta;
type Story = StoryObj<typeof Finish>;

export const Template: Story = {
  args: {
    postUrl: 'https://www.cruru.kr/123543920/recruit',
    postId: '1',
  },
};
