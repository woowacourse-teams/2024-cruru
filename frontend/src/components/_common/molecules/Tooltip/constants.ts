import { TooltipPlacement } from './types';

export const WINDOW_PADDING = 10;

export const OPPOSITE_PLACEMENT: Record<TooltipPlacement, TooltipPlacement> = {
  top: 'bottom',
  topRight: 'bottomLeft',
  right: 'left',
  bottomRight: 'topLeft',
  bottom: 'top',
  bottomLeft: 'topRight',
  left: 'right',
  topLeft: 'bottomRight',
} as const;
