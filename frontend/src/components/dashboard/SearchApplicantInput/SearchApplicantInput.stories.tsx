/* eslint-disable react-hooks/rules-of-hooks */
import type { Meta, StoryObj } from '@storybook/react';
import SearchApplicantInput from '.';

const meta: Meta<typeof SearchApplicantInput> = {
  title: 'Organisms/Dashboard/ApplicantSearchInput',
  component: SearchApplicantInput,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: '',
      },
    },
  },
  args: {},

  tags: ['autodocs'],
  decorators: [
    (Child) => (
      <div
        style={{
          minWidth: '30%',
        }}
      >
        <Child />
      </div>
    ),
  ],
};

export default meta;
type Story = StoryObj<typeof SearchApplicantInput>;

export const Default: Story = {};
