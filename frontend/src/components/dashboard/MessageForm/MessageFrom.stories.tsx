import type { Meta, StoryObj } from '@storybook/react';
import MessageForm from '.';

const meta: Meta<typeof MessageForm> = {
  title: 'Organisms/Dashboard/MessageForm',
  component: MessageForm,
  args: {
    recipient: '러기',
    onSubmit: (data) => {
      alert(`Subject: ${data.subject}, Content: ${data.content}`);
    },
  },
};

export default meta;
type Story = StoryObj<typeof MessageForm>;

export const Default: Story = {};
