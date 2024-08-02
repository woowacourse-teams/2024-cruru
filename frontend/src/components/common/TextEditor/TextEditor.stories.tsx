import type { Meta, StoryObj } from '@storybook/react';
import { useState } from 'react';
import TextEditor from '.';

const meta = {
  title: 'Common/Input/TextEditor',
  component: TextEditor,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: 'ReactQuill 에디터를 wrapping한 TextEditor 컴포넌트입니다.',
      },
    },
  },

  tags: ['autodocs'],

  argTypes: {
    width: { control: 'text' },
    height: { control: 'text' },
    placeholder: { control: 'text' },
    value: { control: false },
    onChange: { control: false },
    onBlur: { action: 'blurred', control: false },
  },

  decorators: [
    (Story) => {
      const [value, setValue] = useState('');
      const handleChange = (content: string) => setValue(content);

      return (
        <div style={{ width: '60rem', height: '40rem' }}>
          <Story
            value={value}
            onChange={handleChange}
          />
        </div>
      );
    },
  ],
} satisfies Meta<typeof TextEditor>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
    value: '',
    placeholder: '내용을 입력하세요...',
    onChange: () => {},
  },
};
