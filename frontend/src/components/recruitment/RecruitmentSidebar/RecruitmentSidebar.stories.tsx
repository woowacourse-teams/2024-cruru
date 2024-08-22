import type { Meta, StoryObj } from '@storybook/react';
import RecruitmentSidebar from './index';

const meta: Meta<typeof RecruitmentSidebar> = {
  title: 'Components/Recruitment/RecruitmentSidebar',
  component: RecruitmentSidebar,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: 'RecruitmentSidebar 컴포넌트는 모집 공고를 작성하고 게시하는 과정을 안내하는 사이드바입니다.',
      },
    },
  },
  tags: ['autodocs'],
  args: {
    options: [
      { text: '첫번째 옵션', isSelected: true },
      { text: '두번째 옵션', isSelected: true },
      { text: '세번째 옵션', isSelected: false },
    ],
  },
  decorators: [
    (Child) => (
      <div
        style={{
          height: '1000px',
          width: '400px',
          backgroundColor: 'gray',
          display: 'flex',
          justifyContent: 'center',
          alignContent: 'center',
        }}
      >
        <Child />
      </div>
    ),
  ],
};

export default meta;
type Story = StoryObj<typeof RecruitmentSidebar>;

export const Default: Story = {};
