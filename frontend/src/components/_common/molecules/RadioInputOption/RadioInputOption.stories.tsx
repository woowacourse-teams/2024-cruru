import { useArgs } from '@storybook/preview-api';
import type { Meta, StoryObj } from '@storybook/react';
import RadioInputOption from '.';

const meta: Meta<typeof RadioInputOption> = {
  title: 'Organisms/Molecules/RadioInputOption',
  component: RadioInputOption,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: 'RadioInputOption 컴포넌트입니다.',
      },
    },
  },
  tags: ['autodocs'],
  argTypes: {
    isChecked: {
      description: '라디오의 체크 상태입니다.',
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
      value: '옵션을 입력하세요.',
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

      return (
        <div style={{ width: '200px' }}>
          <Child args={{ ...args, onCheck, onDeleteBtnClick, inputAttrs: { ...args.inputAttrs, onChange } }} />
        </div>
      );
    },
  ],
};

Default.parameters = {
  docs: {
    description: {
      story: 'RadioInputOption 컴포넌트의 기본 상태입니다.',
    },
  },
};
