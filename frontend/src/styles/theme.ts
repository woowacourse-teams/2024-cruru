import { Theme, css } from '@emotion/react';
import { BaseColors, Colors } from './types';

const baseColors: BaseColors = {
  grayscale: {
    950: '#111111',
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
  redscale: {
    50: '#FEEBEE',
    100: '#FDCDD3',
    200: '#EC9A9C',
    300: '#E17375',
    400: '#EB5454',
    500: '#F04438',
    600: '#E13B3A',
    700: '#CF3133',
    800: '#C22B2C',
    900: '#B32021',
  },
  bluescale: {
    50: '#E9F2FF',
    100: '#C1DBFF',
    200: '#99C3FF',
    300: '#71ACFF',
    400: '#4A95FF',
    500: '#227EFF',
    600: '#0C66E4',
    700: '#004EBB',
    800: '#003D92',
    900: '#002C69',
  },
};

const colors: Colors = {
  text: {
    default: '#172B4D',
    block: '#626F86',
  },
  feedback: {
    error: baseColors.redscale[500],
    warn: '#F79009',
    success: '#12B76A',
  },
  hover: {
    bg: baseColors.grayscale[200],
    border: baseColors.grayscale[600],
  },
  link: {
    default: baseColors.bluescale[600],
    pressed: baseColors.bluescale[700],
  },
  brand: {
    primary: baseColors.purplescale[500],
  },
};

const breakpoints = {
  mobile: '48rem',
  tablet: '76.8rem',
  desktop: '120rem',
};

const typography = {
  family: {
    primary: 'Pretendard',
    code: 'SF Mono, monospace',
  },
  accent: 600,

  heading: {
    900: css`
      font-weight: 700;
      font-size: 3.5rem;
      line-height: 4rem;
      letter-spacing: 0;
    `,
    800: css`
      font-weight: 700;
      font-size: 2.9rem;
      line-height: 3.2rem;
      letter-spacing: 0;
    `,
    700: css`
      font-weight: 700;
      font-size: 2.4rem;
      line-height: 2.8rem;
      letter-spacing: 0;
    `,
    600: css`
      font-weight: 700;
      font-size: 2rem;
      line-height: 2.4rem;
      letter-spacing: 0;
    `,
    500: css`
      font-weight: 600;
      font-size: 1.6rem;
      line-height: 2rem;
      letter-spacing: 0;
    `,
    400: css`
      font-weight: 600;
      font-size: 1.4rem;
      line-height: 1.6rem;
      letter-spacing: 0;
    `,
    300: css`
      font-weight: 600;
      font-size: 1.2rem;
      line-height: 1.6rem;
      letter-spacing: 0;
    `,
    200: css`
      font-weight: 600;
      font-size: 1.2rem;
      line-height: 1.6rem;
      letter-spacing: 0;
    `,
    100: css`
      font-weight: 600;
      font-size: 1.1rem;
      line-height: 1.6rem;
      letter-spacing: 0;
    `,
  },

  common: {
    default: css`
      font-weight: 400;
      font-size: 1.4rem;
      line-height: 2rem;
      letter-spacing: 0;
    `,
    paragraph: css`
      font-weight: 400;
      font-size: 1.4rem;
      line-height: 2.4rem;
      letter-spacing: 0;
    `,
    block: css`
      font-weight: 500;
      font-size: 1.4rem;
      line-height: 2rem;
      letter-spacing: 0;
      margin-bottom: 2.8rem;
    `,
    code: css`
      font-family: 'SF Mono', monospace;
      font-weight: 400;
      font-size: 1.2rem;
      line-height: 2rem;
    `,
    small: css`
      font-weight: 400;
      font-size: 1.2rem;
      line-height: 1.4rem;
      letter-spacing: 0;
    `,
    smallAccent: css`
      font-weight: 700;
      font-size: 1.2rem;
      line-height: 1.4rem;
      letter-spacing: 0;
    `,
    smallBlock: css`
      font-weight: 500;
      font-size: 1.2rem;
      line-height: 2rem;
      letter-spacing: 0;
    `,
    large: css`
      font-weight: 400;
      font-size: 1.6rem;
      line-height: 2rem;
      letter-spacing: 0;
    `,
    largeAccent: css`
      font-weight: 700;
      font-size: 1.6rem;
      line-height: 2rem;
      letter-spacing: 0;
    `,
    largeBlock: css`
      font-weight: 500;
      font-size: 1.6rem;
      line-height: 2rem;
      letter-spacing: 0;
    `,
  },
};

const theme: Theme = {
  baseColors,
  colors,
  breakpoints,
  typography,
};

export default theme;
