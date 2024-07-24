const { merge } = require('webpack-merge');
const common = require('./webpack.config.js');
const Dotenv = require('dotenv-webpack');

module.exports = merge(common, {
  mode: 'production',
  devtool: 'hidden-source-map',
  plugins: [
    new Dotenv({
      path: './.env.production',
      systemvars: true,
      safe: true,
    }),
  ],
});
