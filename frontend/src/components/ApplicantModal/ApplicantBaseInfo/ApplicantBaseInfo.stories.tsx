import type { Meta, StoryObj } from '@storybook/react';
import ApplicantBaseInfo from '.';

const meta: Meta<typeof ApplicantBaseInfo> = {
  title: 'Organisms/ApplicantModal/ApplicantBaseInfo',
  component: ApplicantBaseInfo,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: '모달의 지원자 기본 정보 컴포넌트입니다.',
      },
    },
  },
  tags: ['autodocs'],
  decorators: [
    (Story) => (
      <div style={{ padding: '20px', minWidth: '400px' }}>
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

Default.parameters = {
  docs: {},
};
