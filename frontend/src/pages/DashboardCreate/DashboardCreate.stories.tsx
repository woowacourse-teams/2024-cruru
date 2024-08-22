import type { Meta, StoryObj } from '@storybook/react';
import { reactRouterParameters, withRouter } from 'storybook-addon-remix-react-router';
import DashboardCreate from './index';

const meta: Meta<typeof DashboardCreate> = {
  title: 'Pages/DashboardCreate',
  component: DashboardCreate,
  decorators: [withRouter],
  parameters: {
    layout: 'fullscreen',
    docs: {
      description: {
        component: 'DashboardCreate 컴포넌트는 공고를 작성하고 지원서를 구성한 후 게시할 수 있는 양식을 제공합니다.',
      },
    },
    reactRouter: reactRouterParameters({
      location: {
        pathParams: { dashboardId: '1' },
      },
      routing: { path: '/dashboard/:dashboardId/create' },
    }),
  },
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<typeof DashboardCreate>;

export const Default: Story = {
  args: {},
};
