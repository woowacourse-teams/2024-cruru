/* eslint-disable react-hooks/rules-of-hooks */
import { useState } from 'react';
import { RecruitmentInfoState } from '@customTypes/dashboard';
import type { Meta, StoryObj } from '@storybook/react';
import Recruitment from './index';

const meta: Meta<typeof Recruitment> = {
  title: 'Organisms/Dashboard/Create/Recruitment',
  component: Recruitment,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: 'Recruitment 컴포넌트는 모집 공고를 작성하고 인터넷에 게시할 수 있는 양식을 제공합니다.',
      },
    },
  },
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<typeof Recruitment>;

export const Default: Story = {
  render: (args) => {
    const [recruitmentInfoState, setRecruitmentInfoState] = useState<RecruitmentInfoState>({
      startDate: '',
      endDate: '',
      title: '',
      postingContent: '',
    });

    const nextStep = () => {
      alert('다음 단계로 이동합니다.');
    };

    return (
      <div style={{ width: '800px' }}>
        <Recruitment
          {...args}
          recruitmentInfoState={recruitmentInfoState}
          setRecruitmentInfoState={setRecruitmentInfoState}
          nextStep={nextStep}
        />
      </div>
    );
  },
};
