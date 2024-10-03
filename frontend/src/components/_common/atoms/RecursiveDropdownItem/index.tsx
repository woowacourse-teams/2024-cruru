import DropdownSubTrigger from '@components/_common/molecules/DropdownSubTrigger';
import DropdownItem from '../DropdownItem';

interface BaseItem {
  id: number | string;
  name: string;
  isHighlight?: boolean;
  hasSeparate?: boolean;
}

interface ClickableItem extends BaseItem {
  type: 'clickable';
  onClick: ({ targetProcessId }: { targetProcessId: number }) => void;
}

interface SubTrigger extends BaseItem {
  type: 'subTrigger';
  items: (ClickableItem | SubTrigger)[];
}

export type DropdownItemType = ClickableItem | SubTrigger;

interface RecursiveDropdownRendererProps {
  items: DropdownItemType[];
  size: 'sm' | 'md';
}

export default function RecursiveDropdownItem({ items, size }: RecursiveDropdownRendererProps) {
  const renderItem = (item: DropdownItemType) => {
    if (item.type === 'clickable') {
      return (
        <DropdownItem
          key={item.id}
          size={size}
          onClick={() => {
            item.onClick({ targetProcessId: Number(item.id) });
          }}
          item={item.name}
          isHighlight={item.isHighlight}
          hasSeparate={item.hasSeparate}
        />
      );
    }
    if (item.type === 'subTrigger') {
      return (
        <DropdownSubTrigger
          size={size}
          key={item.id}
          item={item.name}
        >
          <RecursiveDropdownItem
            size={size}
            items={item.items}
          />
        </DropdownSubTrigger>
      );
    }
  };

  return <>{items.map(renderItem)}</>;
}
