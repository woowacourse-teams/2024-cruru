const { merge } = require('webpack-merge');
const common = require('./webpack.config.js');
const Dotenv = require('dotenv-webpack');
const dotenv = require('dotenv');

module.exports = () => {
  dotenv.config({ path: './.env.local' });

  return merge(common, {
    plugins: [
      new Dotenv({
        path: './.env.local',
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
