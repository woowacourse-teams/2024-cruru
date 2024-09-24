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
