import type { Meta, StoryObj } from '@storybook/react';
import RadioOption from '.';

const meta: Meta<typeof RadioOption> = {
  title: 'Common/Molecules/RadioOption',
  component: RadioOption,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: '라디오 버튼과 라벨을 포함한 옵션 컴포넌트입니다.',
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
    label: {
      description: '라디오 버튼의 라벨입니다.',
      control: { type: 'text' },
      table: {
        type: { summary: 'string' },
      },
    },
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
    label: '라디오 옵션',
    checked: false,
    onChange: () => console.log('RadioOption이 클릭되었습니다.'),
  },
};

Default.parameters = {
  docs: {
    description: {
      story: 'RadioOption 컴포넌트의 기본 상태입니다.',
    },
  },
};
