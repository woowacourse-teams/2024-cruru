/* eslint-disable @tanstack/query/stable-query-client */
import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import ErrorPage from '@pages/ErrorPage';
import SignIn from '@pages/SignIn';
import SignUp from '@pages/SignUp';
import Dashboard from '@pages/Dashboard';
import RecruitmentPost from '@pages/RecruitmentPost';
import ConfirmApply from '@pages/ConfirmApply';
import DashboardLayout from '@pages/DashboardLayout';
import DashboardList from '@pages/DashBoardList';
import DashboardCreate from '@pages/DashboardCreate';
import { useToast } from '@contexts/ToastContext';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import App from '../App';

const router = createBrowserRouter(
  [
    {
      path: '/',
      element: <App />,
      errorElement: <ErrorPage />,
      children: [
        {
          path: '/sign-in',
          element: <SignIn />,
        },
        {
          path: '/sign-up',
          element: <SignUp />,
        },
        {
          path: 'post/:postId',
          element: <RecruitmentPost />,
        },
        {
          path: 'post/:postId/confirm',
          element: <ConfirmApply />,
        },
        {
          path: 'dashboard/:dashboardId/',
          element: <DashboardLayout />,
          children: [
            {
              path: ':postId',
              element: <Dashboard />,
            },
            {
              path: 'posts',
              element: <DashboardList />,
            },
          ],
        },
        {
          path: 'dashboard/:dashboardId/create',
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
      <RouterProvider router={router} />
    </QueryClientProvider>
  );
}
