import type { Meta, StoryObj } from '@storybook/react';

import Dropdown, { DropdownProps } from './index';

export default {
  component: Dropdown,
  title: 'Dropdown',
} as Meta;

const Template: StoryObj<DropdownProps> = {
  render: (args) => <Dropdown {...args} />,
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
