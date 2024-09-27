import { Branded } from './utilTypes';

export interface PopOverMenuItem {
  id: number | string;
  name: string;
  isHighlight?: boolean;
  onClick: ({ targetProcessId }: { targetProcessId: number | string }) => void;
  hasSeparate?: boolean;
}

export interface DropdownListItem {
  id: number | string;
  name: string;
  isHighlight?: boolean;
  onClick: ({ targetProcessId }: { targetProcessId: number | string }) => void;
  hasSeparate?: boolean;
}

export type ISO8601 = Branded<string, 'ISO8601'>;
