import { forwardRef } from 'react';

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
    const style: React.CSSProperties = {
      left: `${positionX}px`,
      top: `${positionY}px`,
      maxWidth: maxWidth ? `${maxWidth}px` : undefined,
    };

    return (
      <S.ContentContainer
        ref={ref}
        placement={placement}
        zIndex={zIndex}
        data-visible={isVisible}
        style={style}
      >
        {content}
      </S.ContentContainer>
    );
  },
);

export default TooltipContent;
