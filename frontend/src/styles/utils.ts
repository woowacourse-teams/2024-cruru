import { css } from '@emotion/react';

export const visibleStyles = css`
  opacity: 1;
  pointer-events: auto;
  width: 100%;
  height: 80%;
  padding: 20px;
`;

export const hiddenStyles = css`
  opacity: 0;
  pointer-events: none;
  width: 0;
  height: 0;
  padding: 0;
`;

export const hideScrollBar = css`
  &::-webkit-scrollbar {
    display: none;
  }
  -ms-overflow-style: none;
  scrollbar-width: none;
`;
