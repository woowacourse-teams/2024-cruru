import { useState } from 'react';
import type { Meta, StoryObj } from '@storybook/react';
import CheckboxLabelField from './index';

const meta: Meta<typeof CheckboxLabelField> = {
  title: 'Common/Checkbox/CheckboxLabelField',
  component: CheckboxLabelField,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: 'CheckboxLabelField 컴포넌트는 라벨과 설명, 체크박스 옵션들을 포함한 필드입니다.',
      },
    },
  },
  tags: ['autodocs'],
  argTypes: {
    label: {
      description: '필드의 라벨입니다.',
      control: { type: 'text' },
      table: {
        type: { summary: 'string' },
      },
    },
    description: {
      description: '필드의 설명입니다.',
      control: { type: 'text' },
      table: {
        type: { summary: 'string' },
      },
    },
    error: {
      description: '에러 메시지입니다.',
      control: { type: 'text' },
      table: {
        type: { summary: 'string' },
      },
    },
    disabled: {
      description: '필드를 비활성화합니다.',
      control: { type: 'boolean' },
      table: {
        type: { summary: 'boolean' },
      },
    },
    required: {
      description: '필드를 필수로 표시합니다.',
      control: { type: 'boolean' },
      table: {
        type: { summary: 'boolean' },
      },
    },
    options: {
      description: '체크박스 옵션 배열입니다.',
      control: { type: 'object' },
      table: {
        type: { summary: 'Option[]' },
      },
    },
  },
};

export default meta;
type Story = StoryObj<typeof CheckboxLabelField>;

export const Default: Story = {
  args: {
    label: '옵션 선택',
    description: '원하는 옵션을 선택하세요.',
    error: '',
    disabled: false,
    required: false,
  },
  decorators: [
    (Child, context) => {
      const [options, setOptions] = useState([
        { optionLabel: '옵션 1', isChecked: false },
        { optionLabel: '옵션 2', isChecked: true },
      ]);

      const toggleOption = (index: number) => {
        const newOptions = [...options];
        newOptions[index].isChecked = !newOptions[index].isChecked;
        setOptions(newOptions);
      };

      return (
        <Child
          args={{
            ...context.args,
            options: options.map((value, index) => ({ ...value, onToggle: () => toggleOption(index) })),
          }}
        />
      );
    },
  ],
};

export const LongOptionLabel: Story = {
  args: {
    label: '옵션 선택',
    description: '원하는 옵션을 선택하세요.',
    error: '',
    disabled: false,
    required: false,
  },
  decorators: [
    (Child, context) => {
      const [options, setOptions] = useState([
        { optionLabel: '옵션, 옵션, 옵션, 옵션, 옵션, 옵션, 옵션, 옵션, 옵션1', isChecked: false },
        { optionLabel: '옵션 2', isChecked: true },
      ]);

      const toggleOption = (index: number) => {
        const newOptions = [...options];
        newOptions[index].isChecked = !newOptions[index].isChecked;
        setOptions(newOptions);
      };

      return (
        <div style={{ width: '200px' }}>
          <Child
            args={{
              ...context.args,
              options: options.map((value, index) => ({ ...value, onToggle: () => toggleOption(index) })),
            }}
          />
        </div>
      );
    },
  ],
};
