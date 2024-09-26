import type { Meta, StoryObj } from '@storybook/react';

import Dropdown, { DropdownProps } from '.';

export default {
  component: Dropdown,
  title: 'Common/Molecules/Dropdown',
} as Meta;

const Template: StoryObj<DropdownProps> = {
  render: (args) => <Dropdown {...args}>1323</Dropdown>,
};

const testItem = {
  name: 'Menu Label',
  onClick: () => console.log('clicked'),
};

const testItemList = Array.from({ length: 3 }, (_, index) => ({ ...testItem, id: index, isHighlight: index === 2 }));

export const Default: StoryObj<DropdownProps> = {
  ...Template,
  args: {
    size: 'md',
    items: testItemList,
  },
};

export const SmallSize: StoryObj<DropdownProps> = {
  ...Template,
  args: {
    size: 'sm',
    items: testItemList,
  },
};

export const InitValueTest: StoryObj<DropdownProps> = {
  ...Template,
  args: {
    size: 'md',
    items: testItemList,
    initValue: '아무 글자',
  },
};

export const SeparateTest: StoryObj<DropdownProps> = {
  ...Template,
  args: {
    size: 'sm',
    items: [...testItemList, { ...testItem, name: 'SeparateTest', id: testItemList.length, hasSeparate: true }],
    initValue: '아무 글자',
  },
};
