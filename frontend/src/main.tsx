import { ModalProvider } from '@contexts/ModalContext';
import * as Sentry from '@sentry/react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import React from 'react';
import ReactDOM from 'react-dom/client';

import { Global, ThemeProvider } from '@emotion/react';

import { BASE_URL } from '@constants/constants';
import globalStyles from './styles/globalStyles';
import theme from './styles/theme';

import AppRouter from './router/AppRouter';

Sentry.init({
  dsn: process.env.SENTRY_DSN,
  integrations: [Sentry.browserTracingIntegration(), Sentry.replayIntegration()],
  // Performance Monitoring
  tracesSampleRate: 1.0, //  Capture 100% of the transactions
  tracePropagationTargets: ['localhost', BASE_URL],
  // Session Replay
  replaysSessionSampleRate: 0.1,
  replaysOnErrorSampleRate: 1.0,
});

async function setDevMode() {
  if (process.env.NODE_ENV === 'development') {
    Sentry.getCurrentScope().setLevel('info');
    const worker = await import('@mocks/browser');
    await worker.default.start();
  }
}

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
            <AppRouter />
          </ThemeProvider>
        </QueryClientProvider>
      </ModalProvider>
    </React.StrictMode>,
  );
});
