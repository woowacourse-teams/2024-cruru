import type { Meta, StoryObj } from '@storybook/react';
import QuestionBox from './index';

const meta: Meta<typeof QuestionBox> = {
  title: 'Common/QuestionBox/QuestionBox',
  component: QuestionBox,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component:
          '헤더와 콘텐츠 영역이 있는 간단한 질문 박스 컴포넌트입니다. 콘텐츠는 텍스트 또는 파일 업로드일 수 있습니다.',
      },
    },
  },
  tags: ['autodocs'],
  argTypes: {
    header: {
      description: '헤더 텍스트입니다.',
      control: { type: 'text' },
      table: {
        type: { summary: 'string' },
      },
    },
    type: {
      description: '콘텐츠 타입입니다. 텍스트 또는 파일을 선택할 수 있습니다.',
      control: { type: 'select' },
      options: ['text', 'file'],
      table: {
        type: { summary: 'text | file' },
      },
    },
    content: {
      description: '텍스트 콘텐츠입니다.',
      control: { type: 'text' },
      table: {
        type: { summary: 'string' },
      },
    },
    fileName: {
      description: '파일 이름입니다.',
      control: { type: 'text' },
      table: {
        type: { summary: 'string' },
      },
    },
    onFileDownload: {
      description: '파일 다운로드 시 호출되는 콜백 함수입니다.',
      action: 'downloaded',
      table: {
        type: { summary: '() => void' },
      },
    },
  },
};

export default meta;
type Story = StoryObj<typeof meta>;

export const TextContent: Story = {
  args: {
    header: '질문 예시',
    type: 'text',
    content: '뽑아주세요.',
  },
};

TextContent.parameters = {
  docs: {
    description: {
      story: 'QuestionBox 컴포넌트의 텍스트 콘텐츠 상태입니다.',
    },
  },
};

export const FileContent: Story = {
  args: {
    header: '이력서',
    type: 'file',
    fileName: '이력서 파일 명_어쩌구.pdf',
    onFileDownload: () => console.log('File downloaded'),
  },
};

export const LongContent: Story = {
  args: {
    header: '이력서',
    type: 'text',
    content:
      // eslint-disable-next-line max-len
      '행정권은 대통령을 수반으로 하는 정부에 속한다. 대통령은 국가의 원수이며, 외국에 대하여 국가를 대표한다. 이 헌법은 1988년 2월 25일부터 시행한다. 다만, 이 헌법을 시행하기 위하여 필요한 법률의 제정·개정과 이 헌법에 의한 대통령 및 국회의원의 선거 기타 이 헌법시행에 관한 준비는 이 헌법시행 전에 할 수 있다. 원장은 국회의 동의를 얻어 대통령이 임명하고, 그 임기는 4년으로 하며, 1차에 한하여 중임할 수 있다. 지방자치단체는 주민의 복리에 관한 사무를 처리하고 재산을 관리하며, 법령의 범위안에서 자치에 관한 규정을 제정할 수 있다. 대통령은 국회에 출석하여 발언하거나 서한으로 의견을 표시할 수 있다.',
  },
};

FileContent.parameters = {
  docs: {
    description: {
      story: 'QuestionBox 컴포넌트의 파일 콘텐츠 상태입니다.',
    },
  },
};
