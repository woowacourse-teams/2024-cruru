import type { Meta, StoryObj } from '@storybook/react';
import { FloatingEmailFormProvider } from '@contexts/FloatingEmailFormContext';
import MessageForm from '.';

function Content({
  showHeader,
  onSubmit,
  onClose,
  recipient,
}: {
  showHeader: boolean;
  onSubmit: (data: { subject: string; content: string }) => void;
  onClose: () => void;
  recipient: string;
}) {
  return (
    <>
      {showHeader && (
        <MessageForm.Header
          recipient={recipient}
          onClose={onClose}
        />
      )}
      <MessageForm.Form
        onSubmit={onSubmit}
        isPending={false}
      />
    </>
  );
}

function Template({ showHeader, floating }: { showHeader: boolean; floating: boolean }) {
  const handleSubmit = (data: { subject: string; content: string }) => {
    alert(`Subject: ${data.subject}, Content: ${data.content}`);
  };

  if (floating) {
    return (
      <MessageForm>
        <MessageForm.FloatingBody>
          <Content
            showHeader={showHeader}
            onSubmit={handleSubmit}
            onClose={() => alert('close button clicked')}
            recipient="러기"
          />
        </MessageForm.FloatingBody>
      </MessageForm>
    );
  }

  return (
    <MessageForm>
      <Content
        showHeader={showHeader}
        onSubmit={handleSubmit}
        onClose={() => alert('close button clicked')}
        recipient="러기"
      />
    </MessageForm>
  );
}

const meta: Meta<typeof Template> = {
  title: 'Organisms/Dashboard/SideFloatingMessageForm/MessageForm',
  component: Template,
  tags: ['autodocs'],
  decorators: [
    (Story) => (
      <FloatingEmailFormProvider>
        <Story />
      </FloatingEmailFormProvider>
    ),
  ],
};

export default meta;
type Story = StoryObj<typeof Template>;

export const SideFloating: Story = {
  args: {
    showHeader: true,
    floating: true,
  },
};

export const ApplicantModal: Story = {
  args: {
    showHeader: false,
    floating: false,
  },
};
