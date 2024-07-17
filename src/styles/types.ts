type ColorScale = '900' | '800' | '700' | '600' | '500' | '400' | '300' | '200' | '100' | '50';

export interface Colors {
  grayscale: {
    [key in ColorScale]: string;
  };
  purplescale: {
    [key in ColorScale]: string;
  };
}

declare module '@emotion/react' {
  export interface Theme {
    colors: Colors;
  }
}
