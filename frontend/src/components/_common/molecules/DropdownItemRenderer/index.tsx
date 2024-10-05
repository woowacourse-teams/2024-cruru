import DropdownSubTrigger from '@components/_common/atoms/DropdownSubTrigger';
import DropdownItem from '../../atoms/DropdownItem';

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

interface DropdownItemRendererProps {
  items: DropdownItemType[];
  size?: 'sm' | 'md';
  subContentPlacement?: 'left' | 'right';
}

export default function DropdownItemRenderer({ items, size = 'sm', subContentPlacement }: DropdownItemRendererProps) {
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
          placement={subContentPlacement}
        >
          <DropdownItemRenderer
            size={size}
            items={item.items}
            subContentPlacement={subContentPlacement}
          />
        </DropdownSubTrigger>
      );
    }
  };

  return <>{items.map(renderItem)}</>;
}
