import React from 'react';

import type { Preview } from '@storybook/react';
import { Global, ThemeProvider } from '@emotion/react';

import globalStyles from '../src/styles/globalStyles';
import theme from '../src/styles/theme';

const preview: Preview = {
  parameters: {
    controls: {
      matchers: {
        color: /(background|color)$/i,
        date: /Date$/i,
      },
    },
  },
  decorators: [
    (Story) => {
      return (
        <>
          <Global styles={globalStyles()} />
          <ThemeProvider theme={theme}>
            <Story />
          </ThemeProvider>
        </>
      );
    },
  ],
};

export default preview;
