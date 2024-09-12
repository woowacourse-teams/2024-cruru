import type { Meta, StoryObj } from '@storybook/react';
import Button from '.';

const meta = {
  title: 'Common/Button/Button',
  component: Button,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: '재사용 가능한 Label Button 컴포넌트입니다. 옵션을 조작해 다양한 스타일을 적용할 수 있습니다. ',
      },
    },
  },

  tags: ['autodocs'],

  argTypes: {
    size: {
      description: '버튼의 사이즈입니다. 두 가지 사이즈를 지원하며 기본값은 md(중간)입니다.',
      control: { type: 'select' },
      options: ['sm', 'md', 'fillContainer'],
      table: {
        type: { summary: 'sm | md | fillContainer' },
      },
    },

    color: {
      description: '버튼의 색상 테마입니다. 4가지 옵션을 지원하며 기본값은 white(흰색)입니다.',
      control: { type: 'select' },
      options: ['white', 'primary', 'secondary', 'error', 'black'],
      table: {
        type: { summary: 'white | black | primary | secondary | error' },
      },
    },

    type: {
      table: { disable: true },
    },

    children: {
      name: 'label',
      description: '버튼 안에 들어갈 텍스트입니다.',
      control: {
        type: 'text',
      },
    },
  },
} satisfies Meta<typeof Button>;

export default meta;
type Story = StoryObj<typeof meta>;

export const SmallButton: Story = {
  args: {
    size: 'sm',
    color: 'white',
    children: 'Button',
  },
};

export const MediumButton: Story = {
  args: {
    size: 'md',
    color: 'white',
    children: 'Button',
  },
};

export const fillButton: Story = {
  args: {
    size: 'fillContainer',
    color: 'white',
    children: 'FillButton',
  },

  decorators: [
    (StoryComponent) => (
      <div style={{ height: '200px', width: '200px' }}>
        <StoryComponent />
      </div>
    ),
  ],
};
