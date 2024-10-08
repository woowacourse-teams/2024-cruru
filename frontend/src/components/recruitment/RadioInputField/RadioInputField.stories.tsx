import { useArgs } from '@storybook/preview-api';
import type { Meta, StoryObj } from '@storybook/react';
import RadioInputField from './index';

const meta: Meta<typeof RadioInputField> = {
  title: 'Organisms/Recruitment/Radio/RadioInputField',
  component: RadioInputField,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: 'RadioInputField 컴포넌트는 여러 개의 RadioInputOption으로 구성된 컴포넌트입니다.',
      },
    },
  },
  tags: ['autodocs'],
  argTypes: {
    choices: {
      description: 'RadioInputOption으로 구성된 옵션 객체 배열입니다.',
      control: { type: 'object' },
      table: {
        type: { summary: 'Option[]' },
      },
    },
    setChoices: {
      description: '옵션 객체 배열을 설정하는 함수입니다.',
      action: 'optionsChanged',
      table: {
        type: { summary: 'React.Dispatch<React.SetStateAction<Option[]>>' },
      },
    },
  },
};

export default meta;
type Story = StoryObj<typeof meta>;

const defaultOptions = [{ choice: '' }];

export const Default: Story = {
  args: {
    choices: defaultOptions,
  },
  decorators: [
    (Child) => {
      const [args, updateArgs] = useArgs();

      return (
        <Child
          args={{
            ...args,
            setChoices: (choices) => {
              updateArgs({ choices });
            },
          }}
        />
      );
    },
  ],
};

Default.parameters = {
  docs: {
    description: {
      story: 'RadioInputField 컴포넌트의 기본 상태입니다.',
    },
  },
};
