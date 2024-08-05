import type { Meta, StoryObj } from '@storybook/react';
import Accordion from './index';
import S from './style';

const meta: Meta<typeof Accordion> = {
  title: 'Components/Accordion',
  component: Accordion,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: 'Accordion 컴포넌트는 제목을 클릭하여 내용을 확장하거나 축소할 수 있는 컴포넌트입니다.',
      },
    },
  },
  tags: ['autodocs'],
  argTypes: {
    title: {
      description: 'Accordion의 제목입니다.',
      control: { type: 'text' },
      table: {
        type: { summary: 'string' },
      },
    },
    children: {
      description: 'Accordion의 내부 요소입니다.',
      control: { type: 'text' },
      table: {
        type: { summary: 'React.ReactNode' },
      },
    },
  },
  args: {
    title: '공고',
  },
};

export default meta;
type Story = StoryObj<typeof Accordion>;

export const Default: Story = {
  render: (args) => (
    <div style={{ width: '200px' }}>
      <Accordion {...args}>
        <S.ListItem>프론트엔드 7기 모집</S.ListItem>
        <S.ListItem>백엔드 7기 모집</S.ListItem>
        <S.ListItem>안드로이드 7기 모집</S.ListItem>
      </Accordion>
    </div>
  ),
};
