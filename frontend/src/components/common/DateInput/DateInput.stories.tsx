import type { Meta, StoryObj } from '@storybook/react';
import { useArgs } from '@storybook/preview-api';
import formatDate from '@utils/formatDate';
import DateInput from './index';

const meta: Meta<typeof DateInput> = {
  title: 'Common/DateInput',
  component: DateInput,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: 'DateInput 컴포넌트는 브라우저 내장 Date Picker를 사용하여 날짜를 선택할 수 있습니다.',
      },
    },
  },
  tags: ['autodocs'],
  decorators: [
    (Story) => {
      const [args, updateArgs] = useArgs();
      const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        updateArgs({ value: e.target.value });
        updateArgs({ innerText: formatDate(e.target.value) });
      };

      return (
        <div style={{ padding: '20px' }}>
          <Story
            args={{
              ...args,
              onChange: handleChange,
            }}
          />
        </div>
      );
    },
  ],
  argTypes: {
    label: {
      description: '입력 필드의 라벨입니다.',
      control: { type: 'text' },
      table: {
        type: { summary: 'string' },
      },
    },
    value: {
      description: '현재 선택된 날짜 값입니다.',
      control: { type: 'text' },
      table: {
        type: { summary: 'string' },
      },
    },
    onChange: {
      description: '날짜 값 변경 시 호출되는 콜백 함수입니다.',
      action: 'changed',
      table: {
        type: { summary: '(value: string) => void' },
      },
    },
    innerText: {
      description: '보여지는 날짜를 입력하는 값입니다.',
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
    label: '종료일',
    value: '',
  },
};

Default.parameters = {
  docs: {
    description: {
      story: 'DateInput 컴포넌트의 기본 상태입니다.',
    },
  },
};
