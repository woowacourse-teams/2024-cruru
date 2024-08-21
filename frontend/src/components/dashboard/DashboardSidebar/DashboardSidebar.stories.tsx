import type { Meta, StoryObj } from '@storybook/react';
import DashboardSidebar from './index';

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
  },
  tags: ['autodocs'],
  decorators: [
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
      { text: '우아한테크코스 6기 프론트엔드', isSelected: true, postId: 1 },
      { text: '우아한테크코스 6기 백엔드', isSelected: false, postId: 2 },
      { text: '우아한테크코스 6기 안드로이드', isSelected: false, postId: 3 },
    ],
  },
};
