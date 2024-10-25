/* eslint-disable @tanstack/query/stable-query-client */
import { useToast } from '@contexts/ToastContext';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import { lazy, Suspense } from 'react';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import LoadingPage from '@pages/LoadingPage';
import Landing from '@pages/Landing';
import App from '../App';
import { PATH } from './path';

const ErrorPage = lazy(() => import(/* webpackChunkName: "ErrorPage" */ '@pages/ErrorPage'));
const SignIn = lazy(() => import(/* webpackChunkName: "SignIn" */ '@pages/SignIn'));
const SignUp = lazy(() => import(/* webpackChunkName: "SignUp" */ '@pages/SignUp'));
const Dashboard = lazy(() => import(/* webpackChunkName: "Dashboard" */ '@pages/Dashboard'));
const RecruitmentPost = lazy(() => import(/* webpackChunkName: "RecruitmentPost" */ '@pages/RecruitmentPost'));
const ConfirmApply = lazy(() => import(/* webpackChunkName: "SignConfirmApplyUp" */ '@pages/ConfirmApply'));
const DashboardLayout = lazy(() => import(/* webpackChunkName: "DashboardLayout" */ '@pages/DashboardLayout'));
const DashboardList = lazy(() => import(/* webpackChunkName: "DashBoardList" */ '@pages/DashBoardList'));
const DashboardCreate = lazy(() => import(/* webpackChunkName: "DashboardCreate" */ '@pages/DashboardCreate'));

const router = createBrowserRouter(
  [
    {
      path: PATH.home,
      element: <App />,
      errorElement: <ErrorPage />,
      children: [
        {
          index: true,
          element: <Landing />,
        },
        {
          path: PATH.signIn,
          element: <SignIn />,
        },
        {
          path: PATH.signUp,
          element: <SignUp />,
        },
        {
          path: PATH.post,
          element: <RecruitmentPost />,
        },
        {
          path: PATH.confirmApply,
          element: <ConfirmApply />,
        },
        {
          path: PATH.dashboard.list,
          element: <DashboardLayout />,
          children: [
            {
              index: true,
              element: <DashboardList />,
            },
            {
              path: `${PATH.dashboard.list}${PATH.dashboard.post}`,
              element: <Dashboard />,
            },
          ],
        },
        {
          path: `${PATH.dashboard.list}${PATH.dashboard.create}`,
          element: <DashboardCreate />,
        },
      ],
    },
  ],
  {
    basename: '/',
  },
);

export default function AppRouter() {
  const { error: alertError } = useToast();
  const queryClient = new QueryClient({
    defaultOptions: {
      queries: {
        throwOnError: true,
        retry: 0,
        gcTime: 0,
      },
      mutations: {
        onError: (error) => {
          alertError(error.message);
        },
      },
    },
  });

  return (
    <QueryClientProvider client={queryClient}>
      <ReactQueryDevtools initialIsOpen={false} />
      <Suspense fallback={<LoadingPage />}>
        <RouterProvider router={router} />
      </Suspense>
    </QueryClientProvider>
  );
}
