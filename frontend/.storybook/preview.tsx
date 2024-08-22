import type { Preview } from '@storybook/react';
import { Global, ThemeProvider } from '@emotion/react';

import globalStyles from '@styles/globalStyles';
import theme from '@styles/theme';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { initialize, mswLoader } from 'msw-storybook-addon';
import handlers from '@mocks/handlers';
import { ModalProvider } from '@contexts/ModalContext';
import ToastProvider from '@contexts/ToastContext';

initialize();

const preview: Preview = {
  parameters: {
    controls: {
      matchers: {
        color: /(background|color)$/i,
        date: /Date$/i,
      },
    },
    msw: {
      handlers: handlers,
    },
  },
  loaders: [mswLoader],
  decorators: [
    (Story) => {
      return (
        <ModalProvider>
          <QueryClientProvider client={new QueryClient()}>
            <Global styles={globalStyles()} />
            <ThemeProvider theme={theme}>
              <ToastProvider>
                <Story />
              </ToastProvider>
            </ThemeProvider>
          </QueryClientProvider>
        </ModalProvider>
      );
    },
  ],
};

export default preview;
