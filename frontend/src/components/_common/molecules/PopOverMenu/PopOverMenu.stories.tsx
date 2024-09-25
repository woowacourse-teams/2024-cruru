import type { Meta, StoryObj } from '@storybook/react';
import type { PopOverMenuItem } from '@customTypes/common';

import PopOverMenu, { PopOverMenuProps } from '.';

export default {
  component: PopOverMenu,
  title: 'Common/Molecules/PopOverMenu',
  args: { isOpen: true },
} as Meta;

const Template: StoryObj<PopOverMenuProps> = {
  render: (args) => <PopOverMenu {...args} />,
};

const testItem: PopOverMenuItem = {
  id: 123,
  name: 'Menu Label',
  onClick: () => alert('clicked'),
};
const testItemList: PopOverMenuItem[] = Array.from({ length: 3 }, (_, index) => ({
  ...testItem,
  id: index,
  isHighlight: index === 2,
}));

export const Default: StoryObj<PopOverMenuProps> = {
  ...Template,
  args: {
    size: 'md',
    items: testItemList,
  },
};

export const SmallSize: StoryObj<PopOverMenuProps> = {
  ...Template,
  args: {
    size: 'sm',
    items: testItemList,
  },
};

export const SeparateTest: StoryObj<PopOverMenuProps> = {
  ...Template,
  args: {
    size: 'sm',
    items: [...testItemList, { ...testItem, name: 'SeparateTest', id: testItemList.length, hasSeparate: true }],
  },
};
