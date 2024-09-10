import { reactRouterParameters, withRouter } from 'storybook-addon-remix-react-router';
import type { Meta, StoryObj } from '@storybook/react';
import DashboardSidebar from '.';

const meta: Meta<typeof DashboardSidebar> = {
  title: 'Components/Dashboard/Sidebar',
  component: DashboardSidebar,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: 'DashboardSidebar 컴포넌트는 로고와 여러 개의 Accordion으로 구성된 사이드바입니다.',
      },
    },
    reactRouter: reactRouterParameters({
      location: {
        pathParams: { dashboardId: '21' },
      },
      routing: { path: '/dashboard/:dashboardId' },
    }),
  },
  args: {
    options: [
      {
        text: '첫번째 옵션',
        isSelected: true,
        dashboardId: '1',
        applyFormId: '10',
      },
      {
        text: '두번째 옵션',
        isSelected: false,
        dashboardId: '2',
        applyFormId: '11',
      },
    ],
  },
  tags: ['autodocs'],
  decorators: [
    withRouter,
    (Child) => (
      <div
        style={{
          height: '1000px',
          width: '400px',
          backgroundColor: 'gray',
          display: 'flex',
          justifyContent: 'center',
          alignContent: 'center',
        }}
      >
        <Child />
      </div>
    ),
  ],
};

export default meta;
type Story = StoryObj<typeof DashboardSidebar>;

export const Default: Story = {
  args: {
    options: [
      { text: '우아한테크코스 6기 프론트엔드', isSelected: true, dashboardId: '1', applyFormId: '10' },
      { text: '우아한테크코스 6기 백엔드', isSelected: false, dashboardId: '2', applyFormId: '11' },
      { text: '우아한테크코스 6기 안드로이드', isSelected: false, dashboardId: '3', applyFormId: '12' },
    ],
  },
};
