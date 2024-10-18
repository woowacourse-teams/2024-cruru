import { css } from '@emotion/react';
import theme from './theme';
import { Breakpoints } from './types';

const media = (breakpoint: keyof Breakpoints) => (style: TemplateStringsArray | string) => css`
  @media (max-width: ${theme.breakpoints[breakpoint]}) {
    ${style}
  }
`;

export default media;
