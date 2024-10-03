/* eslint-disable react/function-component-definition */
import React, { useState } from 'react';
import type { Meta, StoryObj } from '@storybook/react';
import type { DropdownItemType } from '@components/_common/atoms/RecursiveDropdownItem';
import RecursiveDropdownItem from '@components/_common/atoms/RecursiveDropdownItem';
import PopOverMenu, { PopOverMenuProps } from '.';

export default {
  component: PopOverMenu,
  title: 'Common/Molecules/PopOverMenu',
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component:
          'PopOverMenu 컴포넌트는 팝오버 형태의 메뉴를 표시합니다. RecursiveDropdownItem을 사용하여 중첩된 메뉴 구조를 지원합니다.',
      },
    },
  },
  argTypes: {
    size: { control: { type: 'radio', options: ['sm', 'md'] } },
    popOverPosition: { control: 'text' },
  },
} as Meta<PopOverMenuProps>;

const createSampleItems: DropdownItemType[] = [
  {
    id: 1,
    name: 'Option 1',
    type: 'clickable',
    onClick: ({ targetProcessId }) => alert(`Clicked Option 1, processId: ${targetProcessId}`),
  },
  {
    id: 2,
    name: 'Option 2',
    type: 'clickable',
    onClick: ({ targetProcessId }) => alert(`Clicked Option 2, processId: ${targetProcessId}`),
  },
  {
    id: 3,
    name: 'Submenu',
    type: 'subTrigger',
    items: [
      {
        id: 4,
        name: 'Suboption 1',
        type: 'clickable',
        onClick: ({ targetProcessId }) => alert(`Clicked Suboption 1, processId: ${targetProcessId}`),
      },
      {
        id: 5,
        name: 'Suboption 2',
        type: 'clickable',
        onClick: ({ targetProcessId }) => alert(`Clicked Suboption 2, processId: ${targetProcessId}`),
      },
    ],
  },
];

const PopOverMenuWithToggle: React.FC<Omit<PopOverMenuProps, 'isOpen'>> = (props) => {
  const [isOpen, setIsOpen] = useState(false);

  return (
    <div style={{ position: 'absolute' }}>
      <button
        type="button"
        onClick={() => setIsOpen(!isOpen)}
      >
        {isOpen ? 'Close Menu' : 'Open Menu'}
      </button>
      <PopOverMenu
        popOverPosition="20px 0px 0px 0px"
        {...props}
        isOpen={isOpen}
      />
    </div>
  );
};

const Template: StoryObj<Omit<PopOverMenuProps, 'isOpen'>> = {
  render: (args) => <PopOverMenuWithToggle {...args} />,
};

export const Default: StoryObj<Omit<PopOverMenuProps, 'isOpen'>> = {
  ...Template,
  args: {
    size: 'md',
    children: (
      <RecursiveDropdownItem
        items={createSampleItems}
        size="md"
      />
    ),
  },
};

export const SmallSize: StoryObj<Omit<PopOverMenuProps, 'isOpen'>> = {
  ...Template,
  args: {
    size: 'sm',
    children: (
      <RecursiveDropdownItem
        items={createSampleItems}
        size="sm"
      />
    ),
  },
};

export const WithSeparator: StoryObj<Omit<PopOverMenuProps, 'isOpen'>> = {
  ...Template,
  args: {
    size: 'sm',
    children: (
      <RecursiveDropdownItem
        items={[
          ...createSampleItems,
          {
            id: 6,
            name: 'Separated Option',
            type: 'clickable',
            onClick: ({ targetProcessId }) => alert(`Clicked Separated Option, processId: ${targetProcessId}`),
            hasSeparate: true,
          },
        ]}
        size="sm"
      />
    ),
  },
};
