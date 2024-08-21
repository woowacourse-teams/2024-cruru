import * as Sentry from '@sentry/react';
import ReactGA from 'react-ga4';

import React from 'react';
import ReactDOM from 'react-dom/client';

import { ModalProvider } from '@contexts/ModalContext';
import { Global, ThemeProvider } from '@emotion/react';
import ToastProvider from '@contexts/ToastContext';

import globalStyles from './styles/globalStyles';
import theme from './styles/theme';

import AppRouter from './router/AppRouter';

const PROD_URL = 'https://www.cruru.kr';
const DEV_URL = 'https://beta.cruru.kr';

async function setPrev() {
  if (process.env.NODE_ENV === 'development') {
    Sentry.getCurrentScope().setLevel('info');
    const worker = await import('@mocks/browser');
    await worker.default.start();
  }
  if (process.env.NODE_ENV === 'production') {
    ReactGA.initialize(process.env.GA_MEASUREMENT_ID);
  }

  Sentry.init({
    dsn: process.env.SENTRY_DSN,
    integrations: [Sentry.browserTracingIntegration(), Sentry.replayIntegration()],
    // Performance Monitoring
    tracesSampleRate: 1.0, //  Capture 100% of the transactions
    tracePropagationTargets: ['localhost', PROD_URL, DEV_URL],
    // Session Replay
    replaysSessionSampleRate: 0.1,
    replaysOnErrorSampleRate: 1.0,
  });
}

setPrev().then(() => {
  ReactDOM.createRoot(document.getElementById('root')!).render(
    <React.StrictMode>
      <ModalProvider>
        <ThemeProvider theme={theme}>
          <Global styles={globalStyles()} />
          <ToastProvider>
            <AppRouter />
          </ToastProvider>
        </ThemeProvider>
      </ModalProvider>
    </React.StrictMode>,
  );
});
