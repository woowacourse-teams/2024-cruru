export interface PopOverMenuItem {
  id: number;
  name: string;
  isHighlight?: boolean;
  onClick: ({ targetProcessId }: { targetProcessId: number }) => void;
}
