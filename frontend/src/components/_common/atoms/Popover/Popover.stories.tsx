/* eslint-disable react/button-has-type */
import { useState, useRef } from 'react';
import { Meta, StoryObj } from '@storybook/react';
import Popover from '.';

const meta: Meta<typeof Popover> = {
  title: 'Common/Atoms/Popover',
  component: Popover,
  parameters: {
    layout: 'centered',
  },
  decorators: [
    (Story, context) => {
      const [isOpen, setIsOpen] = useState(false);
      const buttonRef = useRef<HTMLButtonElement>(null);

      const handleToggle = () => setIsOpen(!isOpen);
      const handleClose = () => setIsOpen(false);

      return (
        <div style={{ height: '150vh', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
          <button
            ref={buttonRef}
            onClick={handleToggle}
          >
            팝오버 토글
          </button>
          <Story args={{ ...context.args, anchorEl: buttonRef.current, isOpen, onClose: handleClose }} />
        </div>
      );
    },
  ],
};

export default meta;

type Story = StoryObj<typeof Popover>;

export const Default: Story = {
  args: {
    children: (
      <div style={{ padding: '20px', background: 'white', border: '1px solid black' }}>
        <h3>팝오버 내용</h3>
        <p>이것은 팝오버의 내용입니다.</p>
      </div>
    ),
  },
};

export const LongDescription: Story = {
  args: {
    children: (
      <div
        style={{
          padding: '20px',
          background: 'white',
          border: '1px solid black',
          maxHeight: '200px',
          overflowY: 'auto',
        }}
      >
        <h3>스크롤 가능한 팝오버</h3>
        {Array(10)
          .fill(null)
          .map((_, i) => (
            // eslint-disable-next-line react/jsx-one-expression-per-line, react/no-array-index-key
            <p key={i}>이것은 스크롤 가능한 팝오버 내용의 {i + 1}번째 문단입니다.</p>
          ))}
      </div>
    ),
  },
};

export const CustomStyle: Story = {
  args: {
    children: (
      <div
        style={{
          padding: '20px',
          background: 'linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)',
          color: 'white',
          borderRadius: '10px',
          boxShadow: '0 3px 5px 2px rgba(255, 105, 135, .3)',
        }}
      >
        <h3>스타일이 적용된 팝오버</h3>
        <p>이 팝오버에는 사용자 정의 스타일이 적용되었습니다.</p>
      </div>
    ),
  },
};
