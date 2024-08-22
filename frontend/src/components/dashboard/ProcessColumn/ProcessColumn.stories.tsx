import type { Meta, StoryObj } from '@storybook/react';
import { reactRouterParameters } from 'storybook-addon-remix-react-router';
import { SpecificApplicantIdProvider } from '@contexts/SpecificApplicnatIdContext';
import { SpecificProcessIdProvider } from '@contexts/SpecificProcessIdContext';
import ProcessColumn from './index';

const meta: Meta<typeof ProcessColumn> = {
  title: 'Components/ProcessColumn',
  component: ProcessColumn,
  parameters: {
    layout: 'fullscreen',
    docs: {
      description: {
        component: 'ProcessColumn 컴포넌트는 특정 프로세스 내의 지원자 목록을 카드 형태로 보여줍니다.',
      },
    },
    reactRouter: reactRouterParameters({
      location: {
        pathParams: { dashboardId: '1', postId: '1' },
      },
      routing: { path: '/dashboard/:dashboardId/post/:postId' },
    }),
  },
  tags: ['autodocs'],
  argTypes: {
    process: {
      description: '프로세스에 대한 정보를 담고 있는 객체입니다.',
      control: { type: 'object' },
      table: {
        type: { summary: 'Process' },
      },
    },
  },
  decorators: [
    (Child) => (
      <SpecificApplicantIdProvider>
        <SpecificProcessIdProvider>
          <Child />
        </SpecificProcessIdProvider>
      </SpecificApplicantIdProvider>
    ),
  ],
};

export default meta;
type Story = StoryObj<typeof ProcessColumn>;

export const Default: Story = {
  args: {
    process: {
      processId: 1,
      orderIndex: 1,
      name: '서류 접수',
      description: '서류 접수 단계입니다.',
      applicants: [
        {
          applicantId: 1,
          applicantName: '지원자 1',
          createdAt: '2024-08-10T12:00:00',
          isRejected: false,
          evaluationCount: 1,
          averageScore: 4.4,
        },
        {
          applicantId: 2,
          applicantName: '지원자 2',
          createdAt: '2024-08-11T12:00:00',
          isRejected: true,
          evaluationCount: 1,
          averageScore: 2.8,
        },
      ],
    },
  },
};
