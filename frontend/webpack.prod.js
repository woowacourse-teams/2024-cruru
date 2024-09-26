const { merge } = require('webpack-merge');
const common = require('./webpack.config.js');
const webpack = require('webpack');
const { BundleAnalyzerPlugin } = require('webpack-bundle-analyzer');

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
          test: /[\\/]node_modules[\\/](?!quill|quill-delta|react-quill-new|eventemitter3)/,
          name: 'vendors',
        },
        quillVendor: {
          test: /[\\/]node_modules[\\/](quill|quill-delta|react-quill-new|eventemitter3)[\\/]/,
          name: 'quill-vendor',
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
