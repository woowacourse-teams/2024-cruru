import { generatePath } from 'react-router-dom';

export const PATH = {
  home: '/',
  signIn: '/sign-in',
  signUp: '/sign-up',
  post: '/post/:postId',
  confirmApply: '/post/:postId/confirm',
  dashboard: { list: '/dashboard', create: '/create', post: '/:dashboardId/:postId' },
};

export const routes = {
  home: () => generatePath(PATH.home),
  signIn: () => generatePath(PATH.signIn),
  signUp: () => generatePath(PATH.signUp),

  post: ({ postId }: { postId: string }) => generatePath(PATH.post, { postId }),

  confirmApply: ({ postId }: { postId: string }) => generatePath(PATH.confirmApply, { postId }),

  dashboard: {
    list: () => generatePath(PATH.dashboard.list),
    create: () => generatePath(`${PATH.dashboard.list}${PATH.dashboard.create}`),
    post: ({ dashboardId, postId }: { dashboardId: string; postId: string }) =>
      generatePath(`${PATH.dashboard.list}${PATH.dashboard.post}`, { dashboardId, postId }),
  },
};
