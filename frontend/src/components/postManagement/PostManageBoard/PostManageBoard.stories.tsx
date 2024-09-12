import { RecruitmentInfoState } from '@customTypes/dashboard';
import type { Meta, StoryObj } from '@storybook/react';
import PostManageBoard from '.';

export default {
  component: PostManageBoard,
  title: 'common/molecules/postManagement/PostManageBoard',
  parameters: {
    docs: {
      description: {
        component: 'PostManageBoard 컴포넌트는 모집 게시글을 관리하는 UI 컴포넌트입니다.',
      },
    },
  },
  tags: ['autodocs'],
} as Meta;

const mockPostState: RecruitmentInfoState = {
  title: '프론트엔드 개발자 모집',
  startDate: new Date().toISOString(),
  endDate: new Date(Date.now() + 86400000 * 7).toISOString(),
  postingContent: '이곳에 공고 내용을 작성하세요.',
};

const postManagementMock = {
  isLoading: false,
  postState: mockPostState,
  setPostState: () => {},
  modifyPostMutator: {
    mutate: () => alert('게시글 수정 요청'),
  },
};

const loadingMock = {
  isLoading: true,
  postState: null,
  setPostState: () => {},
  modifyPostMutator: {
    mutate: () => alert('게시글 수정 요청'),
  },
};

type StoryType = StoryObj<typeof PostManageBoard>;

export const Default: StoryType = {
  args: {
    applyFormId: '1234',
    ...postManagementMock,
  },
  decorators: [
    (Story) => (
      <div style={{ padding: '20px', maxWidth: '800px' }}>
        <Story />
      </div>
    ),
  ],
};

export const LoadingState: StoryType = {
  args: {
    applyFormId: '1234',
    ...loadingMock,
  },
};
