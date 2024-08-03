import { useArgs } from '@storybook/preview-api';
import type { Meta, StoryObj } from '@storybook/react';
import ToggleSwitch from './index';

const meta: Meta<typeof ToggleSwitch> = {
  title: 'Common/ToggleSwitch',
  component: ToggleSwitch,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: 'ToggleSwitch 컴포넌트는 스위치를 토글할 수 있는 기능을 제공합니다.',
      },
    },
  },
  tags: ['autodocs'],
  argTypes: {
    isChecked: {
      description: '스위치의 체크 상태를 나타냅니다.',
      control: { type: 'boolean' },
      table: {
        type: { summary: 'boolean' },
      },
    },
    isDisabled: {
      description: '스위치의 활성화 상태를 나타냅니다.',
      control: { type: 'boolean' },
      table: {
        type: { summary: 'boolean' },
      },
    },
    onChange: {
      description: '스위치 상태 변경 시 호출되는 콜백 함수입니다.',
      action: 'changed',
      table: {
        type: { summary: '() => void' },
      },
    },
  },
  decorators: [
    (Child, context) => {
      const [args, updateArgs] = useArgs();

      const handleChange = () => {
        updateArgs({ isChecked: !context.args.isChecked });
      };

      return (
        <Child
          args={{
            ...args,
            onChange: handleChange,
          }}
        />
      );
    },
  ],
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    isChecked: false,
  },
};

export const Disabled: Story = {
  args: {
    isChecked: false,
    isDisabled: true,
  },
};

Default.parameters = {
  docs: {
    description: {
      story: 'ToggleSwitch 컴포넌트의 기본 상태입니다.',
    },
  },
};
