import { css } from '@emotion/react';

/**
 * 요소를 보여줄 때 사용하세요.
 * opacity, pointer-events를 다시 적용합니다. width, height, padding, margin은 재정의하지 않습니다.
 * 다른 스타일에 오버라이드되면 적용되지 않을 수 있습니다.
 */
export const visibleStyles = css`
  opacity: 1;
  pointer-events: auto;
`;

/**
 * 요소를 숨길 때 사용하세요.
 * opacity, pointer-events, width, height, padding, margin을 취소합니다.
 * 다른 스타일에 오버라이드되면 적용되지 않을 수 있습니다.
 */
export const hiddenStyles = css`
  opacity: 0;
  pointer-events: none;
  width: 0;
  height: 0;
  padding: 0;
  margin: 0;
`;

export const hideScrollBar = css`
  &::-webkit-scrollbar {
    display: none;
  }
  -ms-overflow-style: none;
  scrollbar-width: none;
`;
