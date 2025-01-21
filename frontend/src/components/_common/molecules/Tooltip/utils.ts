import { TooltipPlacement, TooltipPosition } from './types';
import { OPPOSITE_PLACEMENT, WINDOW_PADDING } from './constants';

interface TooltipPositionProps {
  targetRect: DOMRect;
  tooltipRect: DOMRect;
  placement: TooltipPlacement;
  distanceFromTarget: number;
}

export function calculateTooltipPosition({
  targetRect,
  tooltipRect,
  placement,
  distanceFromTarget,
}: TooltipPositionProps) {
  const viewportWidth = window.innerWidth;
  const viewportHeight = window.innerHeight;

  const basePosition = getBasePosition({ targetRect, tooltipRect, placement, distanceFromTarget });

  if (isPositionWithinViewport({ position: basePosition, tooltipRect, viewportWidth, viewportHeight })) {
    return { ...basePosition, placement };
  }

  const oppositeBasePosition = getBasePosition({
    targetRect,
    tooltipRect,
    placement: OPPOSITE_PLACEMENT[placement],
    distanceFromTarget,
  });

  if (isPositionWithinViewport({ position: oppositeBasePosition, tooltipRect, viewportWidth, viewportHeight })) {
    return { ...oppositeBasePosition, placement: OPPOSITE_PLACEMENT[placement] };
  }

  return { ...basePosition, placement };
}

function getBasePosition({ targetRect, tooltipRect, placement, distanceFromTarget }: TooltipPositionProps) {
  const targetCenterX = targetRect.left + targetRect.width / 2;
  const targetCenterY = targetRect.top + targetRect.height / 2;

  switch (placement) {
    case 'top':
      return {
        x: targetCenterX - tooltipRect.width / 2,
        y: targetRect.top - tooltipRect.height - distanceFromTarget,
      };
    case 'topRight':
      return {
        x: targetRect.right + distanceFromTarget,
        y: targetRect.top - tooltipRect.height - distanceFromTarget,
      };
    case 'right':
      return {
        x: targetRect.right + distanceFromTarget,
        y: targetCenterY - tooltipRect.height / 2,
      };
    case 'bottomRight':
      return {
        x: targetRect.right + distanceFromTarget,
        y: targetRect.bottom + distanceFromTarget,
      };
    case 'bottom':
      return {
        x: targetCenterX - tooltipRect.width / 2,
        y: targetRect.bottom + distanceFromTarget,
      };
    case 'bottomLeft':
      return {
        x: targetRect.left - tooltipRect.width - distanceFromTarget,
        y: targetRect.bottom + distanceFromTarget,
      };
    case 'left':
      return {
        x: targetRect.left - tooltipRect.width - distanceFromTarget,
        y: targetCenterY - tooltipRect.height / 2,
      };
    case 'topLeft':
      return {
        x: targetRect.left - tooltipRect.width - distanceFromTarget,
        y: targetRect.top - tooltipRect.height - distanceFromTarget,
      };
    default:
      return {
        x: targetCenterX - tooltipRect.width / 2,
        y: targetRect.top - tooltipRect.height - distanceFromTarget,
      };
  }
}

interface PositionWithinViewportProps {
  position: TooltipPosition;
  tooltipRect: DOMRect;
  viewportWidth: number;
  viewportHeight: number;
}

function isPositionWithinViewport({
  position,
  tooltipRect,
  viewportWidth,
  viewportHeight,
}: PositionWithinViewportProps) {
  return (
    position.x >= WINDOW_PADDING &&
    position.y >= WINDOW_PADDING &&
    position.x + tooltipRect.width <= viewportWidth - WINDOW_PADDING &&
    position.y + tooltipRect.height <= viewportHeight - WINDOW_PADDING
  );
}
