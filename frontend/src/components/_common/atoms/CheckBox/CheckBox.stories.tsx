import { useArgs } from '@storybook/preview-api';
import type { Meta, StoryObj } from '@storybook/react';
import CheckBox from './index';

const meta: Meta<typeof CheckBox> = {
  title: 'Common/Atoms/CheckBox',
  component: CheckBox,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: 'CheckBox 컴포넌트는 isChecked, onToggle, isDisabled 속성을 받아서 동작하는 체크박스입니다.',
      },
    },
  },
  tags: ['autodocs'],
  argTypes: {
    width: {
      description: '체크박스의 크기를 입력합니다.',
      control: { type: 'number', min: 12, max: 60, step: 2 },
      table: {
        type: { summary: 'number' },
      },
    },
    isChecked: {
      description: '체크박스의 체크 상태입니다.',
      control: { type: 'boolean' },
      table: {
        type: { summary: 'boolean' },
      },
    },
    onToggle: {
      description: '체크박스 상태 변경 시 호출되는 콜백 함수입니다.',
      action: 'toggled',
      table: {
        type: { summary: '() => void' },
      },
    },
    isDisabled: {
      description: '체크박스의 비활성화 상태를 나타냅니다.',
      control: { type: 'boolean' },
      table: {
        type: { summary: 'boolean' },
      },
    },
  },
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    isChecked: false,
    isDisabled: false,
  },
  decorators: [
    (Child, context) => {
      const [args, updateArgs] = useArgs();

      const handleToggle = () => {
        updateArgs({ isChecked: !context.args.isChecked });
      };

      return (
        <Child
          args={{ ...args, onToggle: handleToggle }}
          onToggle={handleToggle}
        />
      );
    },
  ],
};

Default.parameters = {
  docs: {
    description: {
      story: 'CheckBox 컴포넌트의 기본 상태입니다.',
    },
  },
};
