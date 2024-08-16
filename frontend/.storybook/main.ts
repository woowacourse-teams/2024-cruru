import type { StorybookConfig } from '@storybook/react-webpack5';
import path from 'path';

const config: StorybookConfig = {
  stories: ['../src/**/*.mdx', '../src/**/*.stories.@(js|jsx|mjs|ts|tsx)'],
  addons: [
    '@storybook/addon-webpack5-compiler-swc',
    '@storybook/addon-onboarding',
    '@storybook/addon-links',
    '@storybook/addon-essentials',
    '@chromatic-com/storybook',
    '@storybook/addon-interactions',
    'storybook-addon-remix-react-router',
  ],
  framework: {
    name: '@storybook/react-webpack5',
    options: {},
  },
  webpackFinal: async (config) => {
    if (config.resolve) {
      config.resolve.alias = {
        ...config.resolve?.alias,
        '@': path.resolve(__dirname, '../src/'),
        '@api': path.resolve(__dirname, '../src/api/'),
        '@assets': path.resolve(__dirname, '../src/assets/'),
        '@components': path.resolve(__dirname, '../src/components/'),
        '@constants': path.resolve(__dirname, '../src/constants/'),
        '@contexts': path.resolve(__dirname, '../src/contexts/'),
        '@hooks': path.resolve(__dirname, '../src/hooks/'),
        '@pages': path.resolve(__dirname, '../src/pages/'),
        '@styles': path.resolve(__dirname, '../src/styles/'),
        '@customTypes': path.resolve(__dirname, '../src/types/'),
        '@utils': path.resolve(__dirname, '../src/utils/'),
        '@mocks': path.resolve(__dirname, '../src/mocks/'),
        '@domain': path.resolve(__dirname, '../src/domain/'),
      };
    }
    return config;
  },
  swc: () => ({
    jsc: {
      transform: {
        react: {
          runtime: 'automatic',
        },
      },
    },
  }),
  staticDirs: ['../public'],
};
export default config;
