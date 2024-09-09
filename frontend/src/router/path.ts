import { generatePath } from 'react-router-dom';

export const PATH = {
  home: '/',
  signIn: '/sign-in',
  signUp: '/sign-up',
  post: '/post/:applyFormId',
  confirmApply: '/post/:applyFormId/confirm',
  dashboard: { list: '/dashboard', create: '/create', post: '/:dashboardId/:applyFormId' },
};

export const routes = {
  home: () => generatePath(PATH.home),
  signIn: () => generatePath(PATH.signIn),
  signUp: () => generatePath(PATH.signUp),

  post: ({ applyFormId }: { applyFormId: string }) => generatePath(PATH.post, { applyFormId }),

  confirmApply: ({ applyFormId }: { applyFormId: string }) => generatePath(PATH.confirmApply, { applyFormId }),

  dashboard: {
    list: () => generatePath(PATH.dashboard.list),
    create: () => generatePath(`${PATH.dashboard.list}${PATH.dashboard.create}`),
    post: ({ dashboardId, applyFormId }: { dashboardId: string; applyFormId: string }) =>
      generatePath(`${PATH.dashboard.list}${PATH.dashboard.post}`, { dashboardId, applyFormId }),
  },
};
