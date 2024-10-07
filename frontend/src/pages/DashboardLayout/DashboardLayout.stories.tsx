import { reactRouterParameters, withRouter } from 'storybook-addon-remix-react-router';
import { Meta, StoryObj } from '@storybook/react';
import DashboardLayout from '.';

const meta: Meta<typeof DashboardLayout> = {
  title: 'Pages/DashboardLayout',
  component: DashboardLayout,
  decorators: [withRouter],
  parameters: {
    layout: 'fullscreen',
    reactRouter: reactRouterParameters({
      location: {
        pathParams: { dashboardId: '1' },
      },
      routing: { path: '/dashboard' },
    }),
  },
};

export default meta;
type Story = StoryObj<typeof DashboardLayout>;

export const Default: Story = {};
