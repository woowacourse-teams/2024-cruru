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
          filename: 'assets/images/[name]_[contenthash:8][ext]',
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
    },
  },
  output: {
    filename: '[name]_[chunkhash:8].js',
    path: path.resolve(__dirname, 'dist'),
    clean: true,
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: './index.html',
      filename: 'index.html',
    }),
    new ForkTsCheckerWebpackPlugin({
      async: false,
      typescript: {
        configFile: path.resolve(__dirname, 'tsconfig.json'),
      },
    }),
  ],
};
