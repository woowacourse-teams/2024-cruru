import { reactRouterParameters, withRouter } from 'storybook-addon-remix-react-router';
import type { Meta, StoryObj } from '@storybook/react';
import DashboardList from '.';

const meta: Meta<typeof DashboardList> = {
  title: 'Pages/DashboardList',
  component: DashboardList,
  decorators: [withRouter],
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: 'DashboardList 컴포넌트는 여러 대시보드를 리스트 형태로 보여줍니다.',
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
};

export default meta;
type Story = StoryObj<typeof DashboardList>;

export const Default: Story = {
  decorators: [
    (Child) => (
      <div style={{ width: '900px', border: '1px solid gray' }}>
        <Child />
      </div>
    ),
  ],
};
