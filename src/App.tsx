import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import ApplicantManage from './pages/ApplicantManage';

const queryClient = new QueryClient();

export default function App() {
  const router = createBrowserRouter(
    [
      {
        path: '/',
        element: <ApplicantManage />,
      },
    ],
    {
      basename: './', //TODO: 배포할때 해당 루트로 적기
    },
  );

  return (
    <QueryClientProvider client={queryClient}>
      {/*Header, SideBar를 여기에 추가하면 됩니다. */}
      <RouterProvider router={router} />
    </QueryClientProvider>
  );
}
