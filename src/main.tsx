import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import React from 'react';
import ReactDOM from 'react-dom/client';

import { Global, ThemeProvider } from '@emotion/react';
import theme from './styles/theme';
import globalStyles from './styles/globalStyles';

import App from './App';

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <QueryClientProvider client={new QueryClient()}>
      <ThemeProvider theme={theme}>
        <Global styles={globalStyles()} />
        <App />
      </ThemeProvider>
    </QueryClientProvider>
  </React.StrictMode>,
);
