import { reactRouterOutlet, reactRouterParameters, withRouter } from 'storybook-addon-remix-react-router';
import { Meta, StoryObj } from '@storybook/react';
import DashboardList from '@pages/DashBoardList';
import Dashboard from '@pages/Dashboard';

import DashboardLayout from '.';

const meta: Meta<typeof DashboardLayout> = {
  title: 'Pages/DashboardLayout',
  component: DashboardLayout,
  decorators: [withRouter],
  parameters: {
    layout: 'fullscreen',
  },
};

export default meta;
type Story = StoryObj<typeof DashboardLayout>;

export const PostListStory: Story = {
  parameters: {
    reactRouter: reactRouterParameters({
      routing: reactRouterOutlet(<DashboardList />),
    }),
  },
};

export const DashboardStory: Story = {
  parameters: {
    reactRouter: reactRouterParameters({
      routing: reactRouterOutlet(<Dashboard />),
    }),
  },
};
