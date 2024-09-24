/* eslint-disable react-hooks/rules-of-hooks */
import type { Meta, StoryObj } from '@storybook/react';
import { reactRouterParameters } from 'storybook-addon-remix-react-router';
import ApplyManagement from './index';

const meta: Meta<typeof ApplyManagement> = {
  title: 'Organisms/ApplyManagement',
  component: ApplyManagement,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: 'ApplyManagement 컴포넌트는 해당 공고의 지원서 사전 질문들을 수정하는 컴포넌트입니다.',
      },
    },
    reactRouter: reactRouterParameters({
      location: {
        pathParams: { applyFormId: '1' },
      },
      routing: { path: '/applyFormId/:applyFormId' },
    }),
  },
  tags: ['autodocs'],
  decorators: [
    (Child) => (
      <div
        style={{
          minWidth: '700px',
        }}
      >
        <Child />
      </div>
    ),
  ],
};

export default meta;
type Story = StoryObj<typeof ApplyManagement>;

export const Default: Story = {
  render: () => (
    <div style={{ width: '700px' }}>
      <ApplyManagement />
    </div>
  ),
};
