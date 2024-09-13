import type { Meta, StoryObj } from '@storybook/react';
import Header from '.';

const meta: Meta<typeof Header> = {
  title: 'Organisms/ApplicantModal/ApplicantDetailInfo/AppDetailHeader',
  component: Header,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: '헤더 탭과 콘텐츠 영역이 있는 컴포넌트입니다.',
      },
    },
  },
  tags: ['autodocs'],
  argTypes: {
    headerTabs: {
      description: '헤더 탭 목록입니다.',
      control: { type: 'object' },
      table: {
        type: { summary: 'string[]' },
      },
    },
    activeTabId: {
      description: '현재 활성화된 탭의 ID입니다..',
      control: { type: 'number' },
      table: {
        type: { summary: 'number' },
      },
    },
    content: {
      description: '콘텐츠 텍스트입니다.',
      control: { type: 'text' },
      table: {
        type: { summary: 'string' },
      },
    },
  },
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    headerTabs: [
      {
        id: 0,
        name: '지원서',
        onClick: () => console.log('지원서가 클릭되었습니다.'),
      },
      {
        id: 1,
        name: '이력서',
        onClick: () => console.log('이력서가 클릭되었습니다.'),
      },
    ],
    activeTabId: 0,
    content: '지원 시 접수된 지원서 내용입니다.',
  },
};

Default.parameters = {
  docs: {
    description: {
      story: 'HeaderTabContent 컴포넌트의 기본 상태입니다.',
    },
  },
};
