import { forwardRef, RefObject, useEffect } from 'react';

import { TooltipPlacement } from './types';
import S from './style';

interface TooltipContentProps {
  content: string;
  placement: TooltipPlacement;
  zIndex?: number;
  maxWidth?: number;
  isVisible: boolean;
  positionX: number;
  positionY: number;
}

export const TooltipContent = forwardRef<HTMLDivElement, TooltipContentProps>(
  ({ content, placement, zIndex, maxWidth, isVisible, positionX, positionY }, ref) => {
    useEffect(() => {
      const tooltipObj = ref as RefObject<HTMLDivElement>;
      if (!tooltipObj.current) return;

      tooltipObj.current.style.left = `${positionX}px`;
      tooltipObj.current.style.top = `${positionY}px`;

      if (maxWidth) {
        tooltipObj.current.style.maxWidth = `${maxWidth}px`;
      }
    }, [maxWidth, positionX, positionY, ref]);

    return (
      <S.ContentContainer
        ref={ref}
        placement={placement}
        zIndex={zIndex}
        data-visible={isVisible}
      >
        {content}
      </S.ContentContainer>
    );
  },
);

export default TooltipContent;
