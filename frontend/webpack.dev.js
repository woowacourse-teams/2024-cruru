const { merge } = require('webpack-merge');
const common = require('./webpack.config.js');
const Dotenv = require('dotenv-webpack');

module.exports = () => {
  return merge(common, {
    plugins: [
      new Dotenv({
        path: './.env.local',
      }),
      new Dotenv({
        path: './.env.sentry-build-plugin',
      }),
    ],
    mode: 'development',
    devtool: 'eval-source-map',
    devServer: {
      historyApiFallback: true,
      port: 3000,
      hot: true,
    },
  });
};
