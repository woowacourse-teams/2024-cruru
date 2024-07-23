import type { Meta, StoryObj } from '@storybook/react';
import { useState } from 'react';
import InputField from '.';

const meta = {
  title: 'Common/Input/InputField',
  component: InputField,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component:
          '재사용 가능한 InputField 컴포넌트입니다. label 유무, focus, error, disabled, required 등의 상태에 따라 스타일이 변경됩니다.',
      },
    },
  },

  tags: ['autodocs'],

  argTypes: {
    label: {
      description:
        'Input 위에 표시되는 레이블입니다. 레이블이 전달되지 않는 경우 표시되지 않으며, Input이 required면 라벨 뒤에 *가 표시됩니다.',
      control: { type: 'text' },
    },
    value: {
      description: 'Input의 값입니다.',
      control: false,
    },
    placeholder: {
      description: 'Input에 값이 없을 때 표시되는 placeholder입니다.',
      control: { type: 'text' },
    },
    required: {
      description: 'Input이 필수 입력 필드인 경우 true로 설정합니다.',
      control: { type: 'boolean' },
    },
    error: {
      description: 'Input에 에러가 발생한 경우 에러 메시지를 표시합니다.',
      type: { name: 'string' },
      control: { type: 'text' },
    },
    disabled: {
      description: 'Input이 비활성화된 경우 true로 설정합니다.',
      control: { type: 'boolean' },
    },
    focus: {
      control: false,
      table: {
        disable: true,
      },
    },
  },

  decorators: [
    (Story) => {
      const [value, setValue] = useState('');
      const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => setValue(e.target.value);

      return (
        <div style={{ width: '30rem' }}>
          <Story
            value={value}
            onChange={handleChange}
          />
        </div>
      );
    },
  ],
} satisfies Meta<typeof InputField>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    label: '필드',
    placeholder: '여기에 직접 입력해보세요.',
    required: true,
    disabled: false,
  },
};
