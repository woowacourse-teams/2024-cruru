import type { Meta, StoryObj } from '@storybook/react';
import IconButton from '.';
import EllipsisIcon from '../../assets/icons/ellipsis.svg';

const meta = {
  title: 'Common/Button/IconButton',
  component: IconButton,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: '재사용 가능한 Icon Button 컴포넌트입니다. 옵션을 조작해 다양한 스타일을 적용할 수 있습니다. ',
      },
    },
  },

  tags: ['autodocs'],

  argTypes: {
    size: {
      description: '버튼의 사이즈입니다. 세 가지 사이즈를 지원하며 기본값은 sm(작음)입니다.',
      control: { type: 'select' },
      options: ['sm', 'md', 'lg'],
      table: {
        type: { summary: 'sm | md | lg' },
      },
    },

    outline: {
      description: '버튼의 윤곽선 유무입니다. 기본값은 false(없음)입니다.',
    },

    shape: {
      description: '버튼의 모양입니다. 기본값은 round(둥근 모양)입니다.',
      control: { type: 'select' },
      options: ['round', 'square'],
      table: {
        type: { summary: 'round | square' },
      },
    },

    children: {
      name: 'icon',
      description: '버튼 안에 들어갈 아이콘입니다.',
      control: { type: 'select' },
      options: ['Ellipsis'],
      mapping: {
        Ellipsis: (
          <img
            src={EllipsisIcon}
            alt="더보기 아이콘 버튼"
          />
        ),
      },
      table: {
        type: { summary: 'React.ReactNode' },
      },
    },
  },
} satisfies Meta<typeof IconButton>;

export default meta;
type Story = StoryObj<typeof meta>;

export const ButtonDefault: Story = {
  args: {
    size: 'sm',
    outline: false,
    shape: 'round',
    children: 'Ellipsis',
  },
};
