import type { Meta, StoryObj } from '@storybook/react';

import PopOverMenu, { PopOverMenuProps } from '.';

export default {
  component: PopOverMenu,
  title: 'PopOverMenu',
  args: { isOpen: true },
} as Meta;

const Template: StoryObj<PopOverMenuProps> = {
  render: (args) => <PopOverMenu {...args} />,
};

const testItem = {
  name: 'Menu Label',
  onClick: () => console.log('clicked'),
};

const testItemList = Array.from({ length: 3 }, (_, index) => ({ ...testItem, id: index, isHighlight: index === 2 }));

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
