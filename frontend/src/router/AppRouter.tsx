import Dashboard from '@pages/Dashboard';
import RecruitmentPost from '@pages/RecruitmentPost';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import App from '../App';

const router = createBrowserRouter(
  [
    {
      path: '/',
      element: <App />,
      children: [
        {
          index: true,
          element: <Dashboard />,
        },
      ],
    },
  ],
  {
    basename: '/', // TODO: 배포할때 해당 루트로 적기
  },
);

export default function AppRouter() {
  return <RouterProvider router={router} />;
}
