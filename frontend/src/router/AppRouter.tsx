/* eslint-disable @tanstack/query/stable-query-client */
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';

import ErrorPage from '@pages/ErrorPage';
import SignIn from '@pages/SignIn';
import SignUp from '@pages/SignUp';
import Dashboard from '@pages/Dashboard';
import RecruitmentPost from '@pages/RecruitmentPost';
import ConfirmApply from '@pages/ConfirmApply';
import DashboardLayout from '@pages/DashboardLayout';
import DashboardList from '@pages/DashBoardList';
import DashboardCreate from '@pages/DashboardCreate';
import Landing from '@pages/Landing';
import { useToast } from '@contexts/ToastContext';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import App from '../App';
import { PATH } from './path';

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
      <RouterProvider router={router} />
    </QueryClientProvider>
  );
}
