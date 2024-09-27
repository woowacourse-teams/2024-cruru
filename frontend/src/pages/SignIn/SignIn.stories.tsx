import type { Meta, StoryObj } from '@storybook/react';
import SignIn from '.';

const meta: Meta<typeof SignIn> = {
  title: 'Pages/SignIn',
  component: SignIn,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: 'Login 컴포넌트는 사용자가 이메일과 비밀번호를 입력하고 로그인할 수 있는 폼을 제공합니다.',
      },
    },
  },
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<typeof SignIn>;

export const Default: Story = {};
