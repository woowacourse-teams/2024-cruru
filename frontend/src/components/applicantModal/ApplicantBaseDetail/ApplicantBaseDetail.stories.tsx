import type { Meta, StoryObj } from '@storybook/react';

import ApplicantBaseDetail from './index';

export default {
  component: ApplicantBaseDetail,
  title: 'ApplicantBaseDetail',
  parameters: {
    docs: {
      description: {
        component: '123',
      },
    },
  },
} as Meta;

const Template: StoryObj = {
  render: () => <ApplicantBaseDetail />,
};

export const Default: StoryObj = {
  ...Template,
  args: {},
};

Default.parameters = {
  docs: {
    description: {
      story: 'The default state of the ApplicantBaseDetail component.',
    },
  },
};
