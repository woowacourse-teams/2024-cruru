import type { Meta, StoryObj } from '@storybook/react';
import TextEditor from '.';

const meta = {
  title: 'Common/Input/TextEditor',
  component: TextEditor,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component: 'ReactQuill 에디터를 wrapping한 TextEditor 컴포넌트입니다.',
      },
    },
  },

  tags: ['autodocs'],

  argTypes: {
    width: { control: 'text' },
    height: { control: 'text' },
    placeholder: { control: 'text' },
    value: { control: 'text' },
    onChange: { action: 'changed' },
    onBlur: { action: 'blurred' },
  },

  decorators: [
    (Story) => (
      <div style={{ width: '60rem', height: '40rem' }}>
        <Story />
      </div>
    ),
  ],
} satisfies Meta<typeof TextEditor>;

export default meta;
type Story = StoryObj<typeof meta>;

export const Snow: Story = {
  args: {
    value: `<h1>제목1</h1><h2>제목2</h2><h3>제목3</h3><p><br></p>
            <p>Normal 스타일입니다.</p>
            <p><strong>Normal에 굵은 서체입니다.</strong></p>
            <p><em>Normal에 기울인 서체입니다.</em></p>
            <p><u>Normal에 밑줄이 있는 서체입니다.</u></p>
            <p><br></p><blockquote>인용구입니다.</blockquote><p><br></p>
            <p><a href="https://www.cruru.kr" rel="noopener noreferrer" target="_blank">링크가 걸려있습니다.</a></p>
            <p><br></p><ol><li data-list="ordered"><span class="ql-ui" contenteditable="false"></span>ol1</li>
            <li data-list="ordered"><span class="ql-ui" contenteditable="false"></span>ol2</li></ol><p><br></p>
            <ol><li data-list="bullet"><span class="ql-ui" contenteditable="false"></span>ul1</li>
            <li data-list="bullet"><span class="ql-ui" contenteditable="false"></span>ul2</li></ol><p><br></p>
            <p>왼쪽 정렬</p><p class="ql-align-center">가운데 정렬</p>
            <p class="ql-align-right">오른쪽 정렬</p><p class="ql-align-justify">채우기~~~~~~~~~~</p>
            <p><br></p><p><span style="color: rgb(230, 0, 0);">색깔 입히기</span></p>
            <p><span style="background-color: rgb(255, 255, 102);">배경색 입히기</span></p><p><br></p>`,
    placeholder: '내용을 입력하세요...',
    onChange: (content: string) => console.log('입력 내용:', content),
  },
};

export const Bubble: Story = {
  args: {
    value: `<h1>제목1</h1><h2>제목2</h2><h3>제목3</h3><p><br></p>
            <p>Normal 스타일입니다.</p>
            <p><strong>Normal에 굵은 서체입니다.</strong></p>
            <p><em>Normal에 기울인 서체입니다.</em></p>
            <p><u>Normal에 밑줄이 있는 서체입니다.</u></p>
            <p><br></p><blockquote>인용구입니다.</blockquote><p><br></p>
            <p><a href="https://www.cruru.kr" rel="noopener noreferrer" target="_blank">링크가 걸려있습니다.</a></p>
            <p><br></p><ol><li data-list="ordered"><span class="ql-ui" contenteditable="false"></span>ol1</li>
            <li data-list="ordered"><span class="ql-ui" contenteditable="false"></span>ol2</li></ol><p><br></p>
            <ol><li data-list="bullet"><span class="ql-ui" contenteditable="false"></span>ul1</li>
            <li data-list="bullet"><span class="ql-ui" contenteditable="false"></span>ul2</li></ol><p><br></p>
            <p>왼쪽 정렬</p><p class="ql-align-center">가운데 정렬</p>
            <p class="ql-align-right">오른쪽 정렬</p><p class="ql-align-justify">채우기~~~~~~~~~~</p>
            <p><br></p><p><span style="color: rgb(230, 0, 0);">색깔 입히기</span></p>
            <p><span style="background-color: rgb(255, 255, 102);">배경색 입히기</span></p><p><br></p>`,
    theme: 'bubble',
    placeholder: '내용을 입력하세요...',
    onChange: (content: string) => console.log('입력 내용:', content),
  },
};
