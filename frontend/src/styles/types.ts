import { SerializedStyles } from '@emotion/react';

type ColorScale = '900' | '800' | '700' | '600' | '500' | '400' | '300' | '200' | '100' | '50';

type ColorScaleMap = {
  [key in ColorScale]: string;
};

export interface BaseColors {
  grayscale: ColorScaleMap & { '950': string };
  purplescale: ColorScaleMap;
  redscale: ColorScaleMap;
  bluescale: ColorScaleMap;
}

export interface Colors {
  text: {
    default: string;
    block: string;
  };
  feedback: {
    error: string;
    warn: string;
    success: string;
  };
  hover: {
    bg: string;
    border: string;
  };
  link: {
    default: string;
    pressed: string;
  };
  brand: {
    primary: string;
  };
}

export interface Breakpoints {
  mobile: string;
  tablet: string;
  desktop: string;
}

export interface Typography {
  family: {
    primary: string;
    code: string;
  };
  accent: number;

  heading: {
    [key: string]: SerializedStyles;
  };
  common: {
    default: SerializedStyles;
    paragraph: SerializedStyles;
    block: SerializedStyles;
    code: SerializedStyles;

    small: SerializedStyles;
    smallAccent: SerializedStyles;
    smallBlock: SerializedStyles;

    large: SerializedStyles;
    largeBlock: SerializedStyles;
    largeAccent: SerializedStyles;
  };
}

declare module '@emotion/react' {
  export interface Theme {
    baseColors: BaseColors;
    colors: Colors;
    breakpoints: Breakpoints;
    typography: Typography;
  }
}
