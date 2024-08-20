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
  return <RouterProvider router={router} />;
}
