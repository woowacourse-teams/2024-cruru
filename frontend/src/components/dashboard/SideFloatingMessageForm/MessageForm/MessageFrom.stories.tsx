import type { Meta, StoryObj } from '@storybook/react';
import { FloatingEmailFormProvider } from '@contexts/FloatingEmailFormContext';
import MessageForm from '.';

const meta: Meta<typeof MessageForm> = {
  title: 'Organisms/Dashboard/SideFloatingMessageForm/MessageForm',
  component: MessageForm,
  tags: ['autodocs'],
  args: {
    recipient: '러기',
    onSubmit: (data) => {
      alert(`Subject: ${data.subject}, Content: ${data.content}`);
    },
    onClose: () => alert('close button clied'),
  },
  decorators: [
    (Story) => (
      <FloatingEmailFormProvider>
        <Story />
      </FloatingEmailFormProvider>
    ),
  ],
};

export default meta;
type Story = StoryObj<typeof MessageForm>;

export const Default: Story = {};
