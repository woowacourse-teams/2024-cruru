/* eslint-disable max-len */
import type { Meta, StoryObj } from '@storybook/react';
import Toast from '.';

const meta: Meta<typeof Toast> = {
  title: 'Common/Toast',
  component: Toast,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: 'Toast 컴포넌트는 메시지를 화면에 나타내며, type에 따라 스타일이 달라집니다.',
      },
    },
  },
  tags: ['autodocs'],
  argTypes: {
    message: {
      description: '표시할 메시지입니다.',
      control: { type: 'text' },
      defaultValue: 'This is a toast message',
    },
    type: {
      description: 'Toast의 타입입니다.',
      control: { type: 'select' },
      options: ['default', 'success', 'error', 'primary'],
      defaultValue: 'default',
    },
    visible: {
      description: 'Toast의 렌더링 여부입니다.',
      control: { type: 'boolean' },
      defaultValue: true,
    },
  },
  args: {
    visible: true,
  },
};

export default meta;
type Story = StoryObj<typeof Toast>;

export const Default: Story = {
  args: {
    message: 'Default Alert!',
    type: 'default',
  },
};

export const Success: Story = {
  args: {
    message: 'Success Alert!',
    type: 'success',
  },
};

export const Error: Story = {
  args: {
    message: 'Error Alert!',
    type: 'error',
  },
};

export const Primary: Story = {
  args: {
    message: 'Primary Alert!',
    type: 'primary',
  },
};

export const LongAlert: Story = {
  args: {
    message: 'Primary Alert Primary Alert Primary Alert Primary Alert! ',
    type: 'primary',
  },
};

export const SuperLongAlert: Story = {
  args: {
    message:
      'Primary Alert Primary Alert Primary Alert Primary Alert! Primary Alert Primary Alert Primary Alert Primary  Alert Primary Alert Primary Alert Primary  Alert Primary !!! Alert Primary Alert Primary  Alert Primary Alert Primary Alert Primary',
    type: 'primary',
  },
};
