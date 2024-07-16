import { Theme } from '@emotion/react';
import { Colors } from './types';

const colors: Colors = {
  grayscale: {
    900: '#3C3C3C',
    800: '#606060',
    700: '#818181',
    600: '#979797',
    500: '#C2C2C2',
    400: '#DEDEDE',
    300: '#F0F0F0',
    200: '#F5F5F5',
    100: '#FAFAFA',
    50: '#FFFFFF',
  },
  purplescale: {
    900: '#600975',
    800: '#7C1283',
    700: '#8C178B',
    600: '#9D1E92',
    500: '#AA2298',
    400: '#B844A6',
    300: '#C666B6',
    200: '#D792CB',
    100: '#E7BEDF',
    50: '#F5E5F2',
  },
};

const theme: Theme = {
  colors,
};

export default theme;
