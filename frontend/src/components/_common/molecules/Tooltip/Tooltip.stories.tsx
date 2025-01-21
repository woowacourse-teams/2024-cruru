import type { Meta, StoryObj } from '@storybook/react';
import styled from '@emotion/styled';
import Tooltip from '.';

const meta = {
  title: 'Common/Molecules/Tooltip',
  component: Tooltip,
  parameters: {
    layout: 'centered',
    docs: {
      description: {
        component:
          'Tooltip 컴포넌트입니다. placement 설정값에 따라 노출 위치가 달라집니다. 스크롤, 리사이징, 뷰포트에 모두 대응합니다.',
      },
    },
  },

  tags: ['autodocs'],

  argTypes: {
    content: {
      description: '툴팁 안에 표시되는 텍스트입니다.',
      control: { type: 'text' },
    },
    placement: {
      description: '툴팁을 표시할 방향입니다.',
    },
    zIndex: {
      description: '툴팁을 위치시킬 zIndex 값입니다.',
      control: { type: 'number' },
    },
    distanceFromTarget: {
      description: '툴팁과 대상 요소 사이의 거리(px)를 정합니다.',
      control: { type: 'number' },
    },
    maxWidth: {
      description: '툴팁의 최대 너비(px)를 정합니다.',
      control: { type: 'number' },
    },
  },
} satisfies Meta<typeof Tooltip>;

export default meta;
type Story = StoryObj<typeof Tooltip>;

const Container = styled.div`
  padding: 32px;
  display: flex;
  gap: 32px;
  flex-wrap: wrap;
`;

const PlacementContainer = styled.div`
  position: relative;
  width: 300px;
  height: 300px;

  & > *:nth-child(1) {
    position: absolute;
    left: 0;
    top: 0;
  }

  & > *:nth-child(2) {
    position: absolute;
    left: 50%;
    top: 0;
    transform: translateX(-50%);
  }

  & > *:nth-child(3) {
    position: absolute;
    right: 0;
    top: 0;
  }

  & > *:nth-child(4) {
    position: absolute;
    left: 0;
    top: 50%;
    transform: translateY(-50%);
  }

  & > *:nth-child(5) {
    position: absolute;
    right: 0;
    top: 50%;
    transform: translateY(-50%);
  }

  & > *:nth-child(6) {
    position: absolute;
    left: 0;
    bottom: 0;
  }

  & > *:nth-child(7) {
    position: absolute;
    left: 50%;
    bottom: 0;
    transform: translateX(-50%);
  }

  & > *:nth-child(8) {
    position: absolute;
    right: 0;
    bottom: 0;
  }
`;

const ScrollContainer = styled.div`
  height: 300px;
  overflow-y: scroll;
  padding: 200px 100px;
  border: 1px solid #e2e8f0;
  background: #f8fafc;
`;

const TriggerButton = styled.button`
  padding: 8px 16px;
  background-color: #e2e8f0;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  white-space: nowrap;

  &:hover {
    background-color: #cbd5e1;
  }
`;

export const Default: Story = {
  name: '기본 툴팁',
  args: {
    content: '기본 툴팁입니다.',
    placement: 'top',
  },
  render: ({ content, placement }) => (
    <Container>
      <Tooltip
        content={content}
        placement={placement}
      >
        <TriggerButton>마우스를 올려보세요</TriggerButton>
      </Tooltip>
    </Container>
  ),
};

export const TooltipsByPlacement: Story = {
  name: '방향별 툴팁 표시',
  render: () => (
    <Container>
      <PlacementContainer>
        <Tooltip
          content="좌측 상단으로 표시되는 툴팁입니다"
          placement="topLeft"
        >
          <TriggerButton>좌측 상단</TriggerButton>
        </Tooltip>
        <Tooltip
          content="상단으로 표시되는 툴팁입니다"
          placement="top"
        >
          <TriggerButton>상단</TriggerButton>
        </Tooltip>
        <Tooltip
          content="우측 상단으로 표시되는 툴팁입니다"
          placement="topRight"
        >
          <TriggerButton>우측 상단</TriggerButton>
        </Tooltip>
        <Tooltip
          content="좌측으로 표시되는 툴팁입니다"
          placement="left"
        >
          <TriggerButton>좌측</TriggerButton>
        </Tooltip>
        <Tooltip
          content="우측으로 표시되는 툴팁입니다"
          placement="right"
        >
          <TriggerButton>우측</TriggerButton>
        </Tooltip>
        <Tooltip
          content="좌측 하단으로 표시되는 툴팁입니다"
          placement="bottomLeft"
        >
          <TriggerButton>좌측 하단</TriggerButton>
        </Tooltip>
        <Tooltip
          content="하단으로 표시되는 툴팁입니다"
          placement="bottom"
        >
          <TriggerButton>하단</TriggerButton>
        </Tooltip>
        <Tooltip
          content="우측 하단으로 표시되는 툴팁입니다"
          placement="bottomRight"
        >
          <TriggerButton>우측 하단</TriggerButton>
        </Tooltip>
      </PlacementContainer>
    </Container>
  ),
};

export const TooltipsByDistance: Story = {
  name: '트리거 요소와의 거리 조절',
  render: () => (
    <Container>
      <Tooltip
        content="기본 거리 (8px)"
        placement="top"
      >
        <TriggerButton>기본 거리</TriggerButton>
      </Tooltip>
      <Tooltip
        content="중간 거리 (16px)"
        placement="top"
        distanceFromTarget={16}
      >
        <TriggerButton>중간 거리</TriggerButton>
      </Tooltip>
      <Tooltip
        content="긴 거리 (24px)"
        placement="top"
        distanceFromTarget={24}
      >
        <TriggerButton>긴 거리</TriggerButton>
      </Tooltip>
    </Container>
  ),
};

export const TooltipsByMaxWidth: Story = {
  name: '툴팁 너비 조절',
  render: () => (
    <Container>
      <Tooltip
        content="기본 너비 설정입니다. 컨텐츠의 길이에 따라 자동으로 조절됩니다."
        placement="top"
      >
        <TriggerButton>기본 너비</TriggerButton>
      </Tooltip>
      <Tooltip
        content="최대 너비가 200px로 제한됩니다. 내용이 길어지면 자동으로 줄바꿈이 됩니다."
        placement="top"
        maxWidth={200}
      >
        <TriggerButton>최대 너비 200px</TriggerButton>
      </Tooltip>
    </Container>
  ),
};

export const ScrollTest: Story = {
  name: '스크롤 시 동작 테스트',
  render: () => (
    <ScrollContainer>
      <Tooltip
        content="스크롤을 움직여보세요!"
        placement="right"
      >
        <TriggerButton>스크롤 테스트 버튼</TriggerButton>
      </Tooltip>
    </ScrollContainer>
  ),
};
