const { merge } = require('webpack-merge');
const common = require('./webpack.config.js');
const webpack = require('webpack');
const { BundleAnalyzerPlugin } = require('webpack-bundle-analyzer');
const { sentryWebpackPlugin } = require('@sentry/webpack-plugin');

module.exports = merge(common, {
  mode: 'production',
  devtool: 'hidden-source-map',
  output: {
    filename: '[name].[contenthash].js',
    chunkFilename: '[name].chunk.[contenthash].js',
  },
  plugins: [
    new webpack.DefinePlugin({
      'process.env': JSON.stringify(process.env),
    }),
    new BundleAnalyzerPlugin({
      analyzerMode: 'static',
      reportFilename: 'bundle-report.html',
      openAnalyzer: false,
      excludeAssets: [/node_modules/],
    }),
    sentryWebpackPlugin({
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
  optimization: {
    splitChunks: {
      cacheGroups: {
        defaultVendors: {
          test: /[\\/]node_modules[\\/]/,
          name: 'vendors',
        },
        default: {
          test: /[\\/]src[\\/]/,
          minChunks: 2,
          name: 'common',
        },
      },
    },
  },
});
