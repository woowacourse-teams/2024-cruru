import type { Meta, StoryObj } from '@storybook/react';
import RadioField from './index';

const meta: Meta<typeof RadioField> = {
  title: 'Components/RadioField',
  component: RadioField,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: '여러 개의 라디오 버튼을 포함하여 옵션을 선택할 수 있는 컴포넌트입니다.',
      },
    },
  },
  tags: ['autodocs'],
  decorators: [
    (Story, context) => {
      const handleChange = (value: string) => {
        context.args.onChange(value);
      };

      return (
        <div style={{ padding: '20px' }}>
          <Story
            {...context.args}
            onChange={handleChange}
          />
        </div>
      );
    },
  ],
  argTypes: {
    options: {
      description: '라디오 버튼 옵션 목록입니다.',
      control: { type: 'object' },
      table: {
        type: { summary: 'Option[]' },
      },
    },
    selectedValue: {
      description: '현재 선택된 라디오 버튼의 값입니다.',
      control: { type: 'text' },
      table: {
        type: { summary: 'string' },
      },
    },
    onChange: {
      description: '옵션 선택 시 호출되는 콜백 함수입니다.',
      action: 'changed',
      table: {
        type: { summary: '(value: string) => void' },
      },
    },
  },
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    options: [
      { label: '아주 좋아요', value: 'veryGood' },
      { label: '좋아요', value: 'good' },
      { label: '그저 그래요', value: 'average' },
      { label: '잘 모르겠어요', value: 'bad' },
      { label: '별로에요', value: 'veryBad' },
    ],
    onChange: (value: string) => console.log(`${value}값이 클릭되었습니다.`),
  },
};

Default.parameters = {
  docs: {
    description: {
      story: 'RadioField 컴포넌트의 기본 상태입니다.',
    },
  },
};
