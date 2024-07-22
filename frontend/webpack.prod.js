const { merge } = require('webpack-merge');
const common = require('./webpack.config.js');

module.exports = merge(common, {
  mode: 'production',
  devtool: 'hidden-source-map', // 에러 보고 목적으로 소스맵을 사용할 때 선택
});
