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
  const scrollX = window.scrollX || document.documentElement.scrollLeft;
  const scrollY = window.scrollY || document.documentElement.scrollTop;

  const viewportWidth = window.innerWidth;
  const viewportHeight = window.innerHeight;

  const basePosition = getComputedPosition({
    targetRect,
    tooltipRect,
    placement,
    distanceFromTarget,
    scrollX,
    scrollY,
  });

  if (isPositionWithinViewport({ position: basePosition, tooltipRect, viewportWidth, viewportHeight })) {
    return { ...basePosition, placement };
  }

  const oppositePosition = getComputedPosition({
    targetRect,
    tooltipRect,
    placement: OPPOSITE_PLACEMENT[placement],
    distanceFromTarget,
    scrollX,
    scrollY,
  });

  if (isPositionWithinViewport({ position: oppositePosition, tooltipRect, viewportWidth, viewportHeight })) {
    return { ...oppositePosition, placement: OPPOSITE_PLACEMENT[placement] };
  }

  return { ...basePosition, placement };
}

interface ComputedPositionProps extends TooltipPositionProps {
  scrollX: number;
  scrollY: number;
}

function getComputedPosition({
  targetRect,
  tooltipRect,
  placement,
  distanceFromTarget,
  scrollX,
  scrollY,
}: ComputedPositionProps) {
  const targetCenterX = targetRect.left + targetRect.width / 2 + scrollX;
  const targetCenterY = targetRect.top + targetRect.height / 2 + scrollY;

  switch (placement) {
    case 'top':
      return {
        x: targetCenterX - tooltipRect.width / 2,
        y: targetRect.top + scrollY - tooltipRect.height - distanceFromTarget,
      };
    case 'topRight':
      return {
        x: targetRect.right + scrollX + distanceFromTarget,
        y: targetRect.top + scrollY - tooltipRect.height - distanceFromTarget,
      };
    case 'right':
      return {
        x: targetRect.right + scrollX + distanceFromTarget,
        y: targetCenterY - tooltipRect.height / 2,
      };
    case 'bottomRight':
      return {
        x: targetRect.right + scrollX + distanceFromTarget,
        y: targetRect.bottom + scrollY + distanceFromTarget,
      };
    case 'bottom':
      return {
        x: targetCenterX - tooltipRect.width / 2,
        y: targetRect.bottom + scrollY + distanceFromTarget,
      };
    case 'bottomLeft':
      return {
        x: targetRect.left + scrollX - tooltipRect.width - distanceFromTarget,
        y: targetRect.bottom + scrollY + distanceFromTarget,
      };
    case 'left':
      return {
        x: targetRect.left + scrollX - tooltipRect.width - distanceFromTarget,
        y: targetCenterY - tooltipRect.height / 2,
      };
    case 'topLeft':
      return {
        x: targetRect.left + scrollX - tooltipRect.width - distanceFromTarget,
        y: targetRect.top + scrollY - tooltipRect.height - distanceFromTarget,
      };
    default:
      return {
        x: targetCenterX - tooltipRect.width / 2,
        y: targetRect.top + scrollY - tooltipRect.height - distanceFromTarget,
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
