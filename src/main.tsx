import React from 'react';
import ReactDOM from 'react-dom/client';

import { Global, ThemeProvider } from '@emotion/react';
import theme from './styles/theme';
import globalStyles from './styles/globalStyles';

import App from './App';

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <ThemeProvider theme={theme}>
      <Global styles={globalStyles()} />
      <App />
    </ThemeProvider>
  </React.StrictMode>,
);
