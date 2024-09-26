const { sentryWebpackPlugin } = require('@sentry/webpack-plugin');

const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const ForkTsCheckerWebpackPlugin = require('fork-ts-checker-webpack-plugin');

module.exports = {
  entry: './src/main.tsx',

  module: {
    rules: [
      {
        test: /\.(png|svg|jpe?g|gif|webp)$/i,
        type: 'asset',
        parser: {
          dataUrlCondition: {
            maxSize: 4 * 1024,
          },
        },
        generator: {
          filename: 'assets/images/[contenthash][ext]',
        },
      },
      {
        test: /\.(js|jsx|ts|tsx|mjs)$/,
        exclude: /node_modules/,
        loader: 'esbuild-loader',
        options: {
          target: 'es2020',
        },
      },
      {
        test: /\.css$/i,
        use: ['style-loader', 'css-loader'],
      },
    ],
  },

  resolve: {
    extensions: ['.js', '.jsx', '.ts', '.tsx', '.mjs'],
    alias: {
      '@api': path.resolve(__dirname, 'src/api/'),
      '@assets': path.resolve(__dirname, 'src/assets/'),
      '@components': path.resolve(__dirname, 'src/components/'),
      '@constants': path.resolve(__dirname, 'src/constants/'),
      '@contexts': path.resolve(__dirname, 'src/contexts/'),
      '@hooks': path.resolve(__dirname, 'src/hooks/'),
      '@pages': path.resolve(__dirname, 'src/pages/'),
      '@styles': path.resolve(__dirname, 'src/styles/'),
      '@customTypes': path.resolve(__dirname, 'src/types/'),
      '@utils': path.resolve(__dirname, 'src/utils/'),
      '@mocks': path.resolve(__dirname, 'src/mocks/'),
      '@domain': path.resolve(__dirname, 'src/domain/'),
      '@router': path.resolve(__dirname, 'src/router/'),
    },
  },

  output: {
    path: path.resolve(__dirname, 'dist'),
    publicPath: '/',
  },

  plugins: [
    new HtmlWebpackPlugin({
      template: './index.html',
      filename: 'index.html',
      favicon: './src/assets/favicon/favicon.ico',
      meta: {
        title: {
          name: 'title',
          content: '크루루 | 쉽고 간편한 리크루팅 솔루션',
        },
        description: {
          name: 'description',
          content:
            '크루루는 대학 연합동아리 및 스타트업 리크루팅에 최적화된 지원자 관리 솔루션입니다. 공고 제작, 지원자 관리, 평가 등 리크루팅의 모든 단계를 크루루와 함께 해결하세요.',
        },
        keywords: {
          name: 'keywords',
          content: '크루루, CRURU, 리크루팅, 채용솔루션, 지원자관리, ATS, 연합동아리, 스타트업, 우아한테크코스',
        },
        'og:url': {
          property: 'og:url',
          content: 'http://www.cruru.kr',
        },
        'og:site_name': {
          property: 'og:site_name',
          content: '크루루',
        },
        'og:type': {
          property: 'og:type',
          content: 'website',
        },
        'og:title': {
          property: 'og:title',
          content: '크루루 | 쉽고 간편한 리크루팅 솔루션',
        },
        'og:description': {
          property: 'og:description',
          content:
            '크루루는 대학 연합동아리 및 스타트업 리크루팅에 최적화된 지원자 관리 솔루션입니다. 공고 제작, 지원자 관리, 평가 등 리크루팅의 모든 단계를 크루루와 함께 해결하세요.',
        },
        'og:image': {
          property: 'og:image',
          content: 'https://www.cruru.kr/assets/images/ogCover_1200x630.png',
        },
        'og:image:alt': {
          property: 'og:image:alt',
          content: '크루루 서비스 소개 이미지',
        },
        'og:locale': {
          property: 'og:locale',
          content: 'ko-KR',
        },
      },
    }),
    new ForkTsCheckerWebpackPlugin({
      async: false,
      typescript: {
        configFile: path.resolve(__dirname, 'tsconfig.json'),
      },
    }),
    sentryWebpackPlugin({
      authToken: process.env.SENTRY_AUTH_TOKEN,
      org: 'cruru',
      project: 'cruru-react',
      bundleSizeOptimizations: {
        excludeDebugStatements: true,
        /**
         * 서비스에서 iframe과 shadow dom을 사용하지 않으므로 관련 코드를 제외합니다.
         * 2024.09.24 렛서
         */
        excludeReplayIframe: true,
        excludeReplayShadowDom: true,
      },
    }),
  ],

  devtool: 'source-map',
};
