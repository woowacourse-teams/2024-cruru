import React from 'react';
import ReactDOM from 'react-dom/client';
import * as Sentry from '@sentry/react';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { ModalProvider } from '@contexts/ModalContext';

import { Global, ThemeProvider } from '@emotion/react';

import theme from './styles/theme';
import globalStyles from './styles/globalStyles';

import App from './App';
import Dashboard from './pages/Dashboard';

Sentry.init({
  dsn: process.env.SENTRY_DSN,
  integrations: [Sentry.browserTracingIntegration(), Sentry.replayIntegration()],
  // Performance Monitoring
  tracesSampleRate: 1.0, //  Capture 100% of the transactions
  tracePropagationTargets: ['localhost', process.env.REACT_APP_CRURU_API_URL],
  // Session Replay
  replaysSessionSampleRate: 0.1,
  replaysOnErrorSampleRate: 1.0,
});

async function setDevMode() {
  if (process.env.NODE_ENV === 'development') {
    Sentry.getCurrentScope().setLevel('info');
    const worker = await import('@mocks/browser');
    worker.default.start();
  }
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

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      throwOnError: true,
      retry: 0,
    },
  },
});

setDevMode().then(() => {
  ReactDOM.createRoot(document.getElementById('root')!).render(
    <React.StrictMode>
      <ModalProvider>
        <QueryClientProvider client={queryClient}>
          <ThemeProvider theme={theme}>
            <Global styles={globalStyles()} />
            <RouterProvider router={router} />
          </ThemeProvider>
        </QueryClientProvider>
      </ModalProvider>
    </React.StrictMode>,
  );
});
