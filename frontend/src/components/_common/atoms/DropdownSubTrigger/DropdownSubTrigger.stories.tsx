import type { Meta, StoryObj } from '@storybook/react';
import { DropdownProvider } from '@contexts/DropdownContext';

import DropdownItemRenderer from '@components/_common/molecules/DropdownItemRenderer';
import type { DropdownItemType } from '@components/_common/molecules/DropdownItemRenderer';
import DropdownSubTrigger from '.';

const meta = {
  title: 'Common/Atoms/DropdownSubTrigger',
  component: DropdownSubTrigger,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component:
          'DropdownSubTrigger 컴포넌트는 하위 메뉴를 가진 드롭다운 아이템을 표시합니다. 내부적으로 DropdownItemRenderer을 사용하여 중첩된 메뉴 구조를 지원합니다.',
      },
    },
  },
  tags: ['autodocs'],
  argTypes: {
    item: {
      description: '드롭다운 아이템의 텍스트',
      control: 'text',
    },
    size: {
      description: '드롭다운 아이템의 크기',
      control: { type: 'radio' },
      options: ['sm', 'md'],
    },
    isHighlight: {
      description: '아이템 강조 여부',
      control: 'boolean',
    },
    hasSeparate: {
      description: '구분선 표시 여부',
      control: 'boolean',
    },
    placement: {
      description: '하위 메뉴의 위치',
      control: { type: 'radio' },
      options: ['left', 'right'],
    },
    children: {
      description: '하위 메뉴 아이템들 (DropdownItemRenderer으로 구성)',
      control: 'object',
    },
  },
  decorators: [
    (Story) => (
      <div style={{ width: '150px' }}>
        <DropdownProvider>
          <Story />
        </DropdownProvider>
      </div>
    ),
  ],
} satisfies Meta<typeof DropdownSubTrigger>;

export default meta;
type Story = StoryObj<typeof meta>;

const sampleSubItems: DropdownItemType[] = [
  {
    id: 1,
    name: 'Subitem 1',
    type: 'clickable',
    onClick: ({ targetProcessId }) => alert(`Clicked Subitem 1, processId: ${targetProcessId}`),
  },
  {
    id: 2,
    name: 'Subitem 2',
    type: 'clickable',
    onClick: ({ targetProcessId }) => alert(`Clicked Subitem 2, processId: ${targetProcessId}`),
  },
  {
    id: 3,
    name: 'Nested Submenu',
    type: 'subTrigger',
    items: [
      {
        id: 4,
        name: 'Nested Subitem',
        type: 'clickable',
        onClick: ({ targetProcessId }) => alert(`Clicked Nested Subitem, processId: ${targetProcessId}`),
      },
    ],
  },
];

export const Default: Story = {
  args: {
    item: 'Main Item',
    size: 'md',
    isHighlight: false,
    hasSeparate: false,
    placement: 'right',
    children: <DropdownItemRenderer items={sampleSubItems} />,
  },
};

export const SmallSize: Story = {
  args: {
    ...Default.args,
    size: 'sm',
  },
};

export const Highlighted: Story = {
  args: {
    ...Default.args,
    isHighlight: true,
  },
};

export const WithSeparator: Story = {
  args: {
    ...Default.args,
    hasSeparate: true,
  },
};

export const LeftPlacement: Story = {
  args: {
    ...Default.args,
    placement: 'left',
  },
};

export const ComplexNestedStructure: Story = {
  args: {
    ...Default.args,
    children: (
      <DropdownItemRenderer
        items={[
          ...sampleSubItems,
          {
            id: 5,
            name: 'Another Submenu',
            type: 'subTrigger',
            items: [
              {
                id: 6,
                name: 'Deep Nested Item 1',
                type: 'clickable',
                onClick: ({ targetProcessId }) => alert(`Clicked Deep Nested Item 1, processId: ${targetProcessId}`),
              },
              {
                id: 7,
                name: 'Deep Nested Item 2',
                type: 'clickable',
                onClick: ({ targetProcessId }) => alert(`Clicked Deep Nested Item 2, processId: ${targetProcessId}`),
                isHighlight: true,
              },
            ],
          },
        ]}
      />
    ),
  },
};
