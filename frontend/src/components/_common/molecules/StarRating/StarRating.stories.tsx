import type { Meta, StoryObj } from '@storybook/react';
import { useState } from 'react';
import StarRating from './index';

const meta: Meta<typeof StarRating> = {
  title: 'Common/Molecules/StarRating',
  component: StarRating,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component:
          '별점 평가 컴포넌트입니다. hover 시 별점을 미리보기 할 수 있으며, 클릭 시 별점을 선택할 수 있습니다.',
      },
    },
  },
  argTypes: {
    rating: {
      control: false,
      description: '현재 평가 점수입니다.',
    },
    handleRating: {
      control: false,
      description: '평가 점수를 변경하는 함수입니다.',
    },
  },
  tags: ['autodocs'],
  decorators: [
    (Story) => {
      const [rating, setRating] = useState<number>(0);

      const handleRating = (newRating: number) => {
        setRating(newRating);
      };

      return <Story args={{ rating, handleRating }} />;
    },
  ],
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {};

Default.parameters = {
  docs: {},
};
