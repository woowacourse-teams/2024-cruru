/* eslint-disable no-shadow */
import { PopoverProvider } from '@contexts/PopoverContext';
import { action } from '@storybook/addon-actions';
import type { Meta, StoryObj } from '@storybook/react';
import useFilterApplicant from '@hooks/useProcess/useFilterApplicant';
import RatingFilter from '.';

const meta: Meta<typeof RatingFilter> = {
  title: 'Organisms/RatingFilter',
  component: RatingFilter,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: '평점에 따른 filter 기능을 가진 컴포넌트입니다.',
      },
    },
  },
  tags: ['autodocs'],
  decorators: [
    (Story) => {
      const ratingFilterProps = useFilterApplicant();
      return (
        <PopoverProvider onClose={action('click close')}>
          <div style={{ padding: '20px', backgroundColor: 'gray' }}>
            <Story args={{ ...ratingFilterProps }} />
          </div>
        </PopoverProvider>
      );
    },
  ],
};

export default meta;
type Story = StoryObj<typeof meta>;

export const Default: Story = {
  decorators: [
    (Story) => {
      const { ratingFilterType, ratingRange } = useFilterApplicant();
      action('ratingFilter state가 변경되었습니다.')(
        `RatingFilterType: ${ratingFilterType}, 범위 최소값:${ratingRange.min}, 범위 최댓값: ${ratingRange.max}`,
      );

      return <Story />;
    },
  ],
};
