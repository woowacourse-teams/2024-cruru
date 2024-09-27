import type { Meta, StoryObj } from '@storybook/react';

import DropdownItem, { DropdownItemProps } from '.';

export default {
  component: DropdownItem,
  title: 'Common/Atoms/DropdownItem',
} as Meta;

const Template: StoryObj<DropdownItemProps> = {
  render: (args) => <DropdownItem {...args} />,
};

export const Default: StoryObj<DropdownItemProps> = {
  ...Template,
  args: {
    item: 'Menu Label',
  },
};

export const Highlight: StoryObj<DropdownItemProps> = {
  ...Template,
  args: {
    item: 'Menu Label',
    isHighlight: true,
  },
};
