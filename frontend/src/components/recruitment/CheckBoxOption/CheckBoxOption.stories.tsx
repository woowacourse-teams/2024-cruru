import { useArgs } from '@storybook/preview-api';
import type { Meta, StoryObj } from '@storybook/react';
import CheckBoxOption from './index';

const meta: Meta<typeof CheckBoxOption> = {
  title: 'Components/Recruitment/CheckBox/CheckBoxOption',
  component: CheckBoxOption,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: 'CheckBox, Input, DeleteBtn으로 구성된 CheckBoxOption 컴포넌트입니다.',
      },
    },
  },
  tags: ['autodocs'],
  argTypes: {
    isChecked: {
      description: '체크박스의 체크 상태입니다.',
      control: { type: 'boolean' },
      table: {
        type: { summary: 'boolean' },
      },
    },
    isDisabled: {
      description: '체크박스 및 입력 필드의 비활성화 상태를 나타냅니다.',
      control: { type: 'boolean' },
      table: {
        type: { summary: 'boolean' },
      },
    },
    onCheck: {
      description: '체크박스 상태 변경 시 호출되는 콜백 함수입니다.',
      action: 'checked',
      table: {
        type: { summary: '() => void' },
      },
    },
    onDeleteBtnClick: {
      description: '삭제 버튼 클릭 시 호출되는 콜백 함수입니다.',
      action: 'deleted',
      table: {
        type: { summary: '() => void' },
      },
    },
    inputAttrs: {
      description: '입력 필드의 속성을 나타내는 객체입니다.',
      control: { type: 'object' },
      table: {
        type: { summary: 'React.InputHTMLAttributes<HTMLInputElement>' },
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
    inputAttrs: {
      value: '',
    },
  },
  decorators: [
    (Child) => {
      const [args, updateArgs] = useArgs();

      const onCheck = () => {
        updateArgs({ isChecked: !args.isChecked });
      };

      const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        updateArgs({ inputAttrs: { ...args.inputAttrs, value: e.target.value } });
      };

      const onDeleteBtnClick = () => {
        alert('Delete Button을 클릭하였습니다.');
      };

      return <Child args={{ ...args, onCheck, onDeleteBtnClick, inputAttrs: { ...args.inputAttrs, onChange } }} />;
    },
  ],
};

Default.parameters = {
  docs: {
    description: {
      story: 'CheckBoxOption 컴포넌트의 기본 상태입니다.',
    },
  },
};
