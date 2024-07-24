import type { Meta, StoryObj } from '@storybook/react';
import ApplicantDetailInfo from './index';

const meta: Meta<typeof ApplicantDetailInfo> = {
  title: 'Components/ApplicantDetailInfo',
  component: ApplicantDetailInfo,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: '지원서 및 이력서를 포함한 지원자 상세 정보 컴포넌트입니다.',
      },
    },
  },
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};
Default.parameters = {
  docs: {
    description: {
      story: 'ApplicantDetailInfo 컴포넌트의 기본 상태입니다.',
    },
  },
};
