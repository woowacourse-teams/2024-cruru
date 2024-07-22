import React from 'react';
import ReactDOM from 'react-dom/client';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';

import { Global, ThemeProvider } from '@emotion/react';
import worker from '@mocks/browser';
import theme from './styles/theme';
import globalStyles from './styles/globalStyles';

import App from './App';
import Dashboard from './pages/Dashboard';

if (process.env.NODE_ENV === 'development') {
  worker.start();
}

const router = createBrowserRouter(
  [
    {
      path: '/',
      element: <App />,
      children: [
        {
          path: '/',
          element: <Dashboard />,
        },
      ],
    },
  ],
  {
    basename: '/', // TODO: 배포할때 해당 루트로 적기
  },
);

const queryClient = new QueryClient();

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <QueryClientProvider client={queryClient}>
      <ThemeProvider theme={theme}>
        <Global styles={globalStyles()} />
        <RouterProvider router={router} />
      </ThemeProvider>
    </QueryClientProvider>
  </React.StrictMode>,
);
