/* eslint-disable react/jsx-one-expression-per-line */
import { action } from '@storybook/addon-actions';
import { useState } from 'react';
import { Meta, StoryObj } from '@storybook/react';
import Slider from '.';

const meta: Meta<typeof Slider> = {
  title: 'Common/Atoms/Slider',
  component: Slider,
  argTypes: {
    onRangeChange: { action: 'rangeChanged' },
    isDisabled: { description: '비활성화 여부' },
  },
  decorators: [
    (Story, context) => {
      const [range, setRange] = useState({
        min: context.args.initialMin,
        max: context.args.initialMax,
      });

      const handleRangeChange = (min: number, max: number) => {
        setRange({ min, max });
        action('rangeChanged')(`최대값은 ${max}, 최소값은 ${min}`);
      };

      return (
        <div style={{ width: '300px' }}>
          <Story
            args={{
              ...context.args,
              onRangeChange: handleRangeChange,
            }}
          />
          <div style={{ marginTop: '20px' }}>
            Current Range: {range.min.toFixed(2)} - {range.max.toFixed(2)}
          </div>
        </div>
      );
    },
  ],
};

export default meta;
type Story = StoryObj<typeof Slider>;

export const Default: Story = {
  args: {
    min: 0,
    max: 5,
    step: 0.5,
    initialMin: 0,
    initialMax: 5,
  },
};

export const DecimalStep: Story = {
  args: {
    min: 0,
    max: 5,
    step: 0.1,
    initialMin: 0,
    initialMax: 5,
  },
};

export const NarrowRange: Story = {
  args: {
    min: 0,
    max: 100,
    step: 1,
    initialMin: 40,
    initialMax: 60,
  },
};

export const CustomRange: Story = {
  args: {
    min: -50,
    max: 50,
    step: 5,
    initialMin: -25,
    initialMax: 25,
  },
};

export const Disabled: Story = {
  args: {
    min: -50,
    max: 50,
    step: 5,
    initialMin: -25,
    initialMax: 25,
  },
};
