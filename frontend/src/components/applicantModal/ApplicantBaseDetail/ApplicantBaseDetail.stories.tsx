import type { Meta, StoryObj } from '@storybook/react';
import ApplicantBaseDetail from './index';

const ApplicantBaseDetailContainer = ({ ...props }) => (
  <div style={{ padding: '20px', minWidth: '400px' }}>
    <ApplicantBaseDetail {...props} />
  </div>
);

const meta: Meta<typeof ApplicantBaseDetail> = {
  title: 'Components/ApplicantBaseDetail',
  component: ApplicantBaseDetailContainer,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: '지원자의 기본적인 상세 정보를 나타내는 컴포넌트 입니다.',
      },
    },
  },

  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {},
};

Default.parameters = {
  docs: {
    description: {
      story: 'The default state of the ApplicantBaseDetail component.',
    },
  },
};
