import { PropsWithChildren, useCallback, useEffect, useRef, useState } from 'react';

import TooltipContent from './TooltipContent';
import TooltipPortal from './TooltipPortal';

import { calculateTooltipPosition } from './utils';
import { TooltipPlacement, TooltipPositionWithPlacement } from './types';
import S from './style';

export interface TooltipProps extends PropsWithChildren {
  content: string;
  placement?: TooltipPlacement;
  zIndex?: number;
  distanceFromTarget?: number;
  maxWidth?: number;
}

export default function Tooltip({
  children,
  content,
  placement = 'top',
  zIndex = 9999,
  distanceFromTarget = 8,
  maxWidth,
}: TooltipProps) {
  const [isVisible, setIsVisible] = useState<boolean>(false);
  const [tooltipPosition, setTooltipPosition] = useState<TooltipPositionWithPlacement>({ x: 0, y: 0, placement });

  const triggerRef = useRef<HTMLDivElement>(null);
  const tooltipRef = useRef<HTMLDivElement>(null);

  const updateTooltipPosition = useCallback(() => {
    if (!triggerRef.current || !tooltipRef.current) return;

    const targetRect = triggerRef.current.getBoundingClientRect();
    const tooltipRect = tooltipRef.current.getBoundingClientRect();

    const newPosition = calculateTooltipPosition({ targetRect, tooltipRect, placement, distanceFromTarget });

    setTooltipPosition(newPosition);
  }, [placement, distanceFromTarget]);

  const handleMouseEnter = useCallback(() => {
    setIsVisible(true);
    updateTooltipPosition();
  }, [updateTooltipPosition]);

  const handleMouseLeave = useCallback(() => {
    setIsVisible(false);
  }, []);

  useEffect(() => {
    if (isVisible) {
      updateTooltipPosition();

      window.addEventListener('scroll', updateTooltipPosition, true);
      window.addEventListener('resize', updateTooltipPosition);

      return () => {
        window.removeEventListener('scroll', updateTooltipPosition, true);
        window.removeEventListener('resize', updateTooltipPosition);
      };
    }
  }, [isVisible, updateTooltipPosition]);

  return (
    <>
      <S.TooltipTriggerWrapper
        ref={triggerRef}
        onMouseEnter={handleMouseEnter}
        onMouseLeave={handleMouseLeave}
      >
        {children}
      </S.TooltipTriggerWrapper>

      <TooltipPortal>
        <TooltipContent
          ref={tooltipRef}
          content={content}
          placement={tooltipPosition.placement}
          positionX={tooltipPosition.x}
          positionY={tooltipPosition.y}
          isVisible={isVisible}
          zIndex={zIndex}
          maxWidth={maxWidth}
        />
      </TooltipPortal>
    </>
  );
}
