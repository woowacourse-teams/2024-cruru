import type { Meta, StoryObj } from '@storybook/react';
import Radio from './index';

const meta: Meta<typeof Radio> = {
  title: 'Common/Radio/Base',
  component: Radio,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: '체크할 수 있는 간단한 라디오 버튼 컴포넌트입니다.',
      },
    },
  },
  tags: ['autodocs'],
  decorators: [
    (Story) => (
      <div style={{ padding: '20px' }}>
        <Story />
      </div>
    ),
  ],
  argTypes: {
    checked: {
      description: '라디오 버튼이 체크되었는지 여부를 결정합니다.',
      control: { type: 'boolean' },
      table: {
        type: { summary: 'boolean' },
      },
    },
    onChange: {
      description: '체크 상태 변경 시 호출되는 콜백 함수입니다.',
      action: 'changed',
      table: {
        type: { summary: '() => void' },
      },
    },
  },
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    checked: false,
    onChange: () => console.log('Radio 버튼이 클릭되었습니다!'),
  },
};

Default.parameters = {
  docs: {
    description: {
      story: '라디오 컴포넌트의 기본 상태입니다.',
    },
  },
};

export const SizeRadio: Story = {
  args: {
    checked: false,
    diameter: 50,
    onChange: () => console.log('Radio 버튼이 클릭되었습니다!'),
  },
};
