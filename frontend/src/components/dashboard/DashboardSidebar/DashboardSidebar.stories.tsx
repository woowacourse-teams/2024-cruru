import { useState } from 'react';

import { reactRouterParameters, withRouter } from 'storybook-addon-remix-react-router';
import type { Meta, StoryObj } from '@storybook/react';
import DashboardSidebar from '.';

const meta: Meta<typeof DashboardSidebar> = {
  title: 'Organisms/Dashboard/Sidebar',
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
        isSelected: false,
        dashboardId: 1,
        applyFormId: 10,
        status: {
          isClosed: true,
          isPending: false,
          isOngoing: false,
          status: 'Closed',
        },
      },
      {
        text: '두번째 옵션',
        isSelected: false,
        dashboardId: 2,
        applyFormId: 11,
        status: {
          isClosed: true,
          isPending: false,
          isOngoing: false,
          status: 'Closed',
        },
      },
      {
        text: '세번째 옵션',
        isSelected: true,
        dashboardId: 2,
        applyFormId: 12,
        status: {
          isClosed: false,
          isPending: false,
          isOngoing: true,
          status: 'Ongoing',
        },
      },
      {
        text: '네번째 옵션',
        isSelected: false,
        dashboardId: 2,
        applyFormId: 13,
        status: {
          isClosed: false,
          isPending: true,
          isOngoing: false,
          status: 'Pending',
        },
      },
    ],
  },
  tags: ['autodocs'],
  decorators: [
    withRouter,
    (Child, context) => {
      const [isOpen, setIsOpen] = useState(true);

      const handleToggle = () => {
        if (isOpen) setIsOpen(false);
        if (!isOpen) setIsOpen(true);
      };

      return (
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
          <Child
            args={{
              ...context.args,
              sidebarStyle: { isSidebarOpen: isOpen, onClickSidebarToggle: handleToggle },
            }}
          />
        </div>
      );
    },
  ],
};

export default meta;
type Story = StoryObj<typeof DashboardSidebar>;

export const Default: Story = {};
