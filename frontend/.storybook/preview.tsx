import type { Preview } from '@storybook/react';
import { Global, ThemeProvider } from '@emotion/react';

import globalStyles from '@styles/globalStyles';
import theme from '@styles/theme';

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
