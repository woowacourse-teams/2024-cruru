import type { Meta, StoryObj } from '@storybook/react';
import { DropdownProvider } from '@contexts/DropdownContext';
import DropdownItem, { DropdownItemProps } from '.';

export default {
  component: DropdownItem,
  title: 'Common/Atoms/DropdownItem',
  decorators: [
    (Story) => (
      <DropdownProvider>
        <Story />
      </DropdownProvider>
    ),
  ],
} as Meta;

export const Default: StoryObj<DropdownItemProps> = {
  args: {
    item: 'Menu Label',
  },
};

export const Highlight: StoryObj<DropdownItemProps> = {
  args: {
    item: 'Menu Label',
    isHighlight: true,
  },
};
