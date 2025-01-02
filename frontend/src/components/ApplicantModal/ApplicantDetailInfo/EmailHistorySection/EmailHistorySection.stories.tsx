import type { Meta, StoryObj } from '@storybook/react';
import EmailHistorySection from '.';

const meta: Meta<typeof EmailHistorySection> = {
  title: 'Organisms/ApplicantModal/ApplicantDetailInfo/EmailHistorySection',
  component: EmailHistorySection,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: '지원자에게 전송된 이메일 목록입니다.',
      },
    },
  },
  args: {
    applicantId: 1,
  },
  tags: ['autodocs'],
  decorators: [
    (Story) => (
      <div style={{ width: '600px', height: '500px' }}>
        <Story />
      </div>
    ),
  ],
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    applicantId: 1,
  },
};
