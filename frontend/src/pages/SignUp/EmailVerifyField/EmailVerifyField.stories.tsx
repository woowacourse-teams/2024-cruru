import { useState } from 'react';
import useForm from '@hooks/utils/useForm';

import type { Meta, StoryObj } from '@storybook/react';
import EmailVerifyField from '.';

const meta: Meta<typeof EmailVerifyField> = {
  title: 'Organisms/EmailVerifyField',
  component: EmailVerifyField,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: 'EmailVerifyField 컴포넌트는 이메일 인증을 담당하는 컴포넌트 입니다.',
      },
    },
  },
  decorators: [
    (Story) => {
      const { register } = useForm({ initialValues: { email: '', verification: '' } });
      const [isVerify, setIsVerify] = useState(false);

      return (
        <div style={{ width: '36rem' }}>
          <Story args={{ register, isVerify, setIsVerify }} />
        </div>
      );
    },
  ],
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<typeof EmailVerifyField>;

export const Default: Story = {};
