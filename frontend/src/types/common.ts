export interface PopOverMenuItem {
  id: string;
  name: string;
  isHighlight?: boolean;
  onClick: () => void;
}
