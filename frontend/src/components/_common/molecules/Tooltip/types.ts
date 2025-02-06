export type TooltipPlacement =
  | 'top'
  | 'topRight'
  | 'right'
  | 'bottomRight'
  | 'bottom'
  | 'bottomLeft'
  | 'left'
  | 'topLeft';

export interface TooltipPosition {
  x: number;
  y: number;
}

export interface TooltipPositionWithPlacement extends TooltipPosition {
  placement: TooltipPlacement;
}
