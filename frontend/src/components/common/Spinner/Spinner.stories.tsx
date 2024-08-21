import type { Meta, StoryObj } from '@storybook/react';
import Spinner from '.';

const meta = {
  title: 'Common/Spinner',
  component: Spinner,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: 'Spinner 컴포넌트입니다.',
      },
    },
  },
  decorators: [
    (Story) => (
      <div style={{ padding: '10px', backgroundColor: '#dcdcdc' }}>
        <Story />
      </div>
    ),
  ],
  tags: ['autodocs'],
} satisfies Meta<typeof Spinner>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};

export const BigSpinner: Story = {
  args: {
    width: 300,
  },
};
