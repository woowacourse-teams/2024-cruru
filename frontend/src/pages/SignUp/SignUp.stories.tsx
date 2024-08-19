import type { Meta, StoryObj } from '@storybook/react';
import Login from '.';

const meta: Meta<typeof Login> = {
  title: 'Pages/SignUp',
  component: Login,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: '회원가입 UI 컴포넌트입니다.',
      },
    },
  },
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<typeof Login>;

export const Default: Story = {};
