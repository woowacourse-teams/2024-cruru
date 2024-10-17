/* eslint-disable react/no-array-index-key */
/* eslint-disable array-callback-return */
/* eslint-disable no-use-before-define */
import { useEffect, useRef, useState } from 'react';

import checkElementPosition from '@utils/checkElementPosition';

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
}

function Clickable({ item, size }: { item: ClickableItem; size: 'sm' | 'md' }) {
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

function SubTrigger({
  item,
  size,
  subContentPlacement,
}: {
  item: SubTrigger;
  size: 'sm' | 'md';
  subContentPlacement: 'left' | 'right';
}) {
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
      />
    </DropdownSubTrigger>
  );
}

export default function DropdownItemRenderer({ items, size = 'sm' }: DropdownItemRendererProps) {
  const ref = useRef<HTMLDivElement>(null);
  const [isRight, setIsRight] = useState(false);

  useEffect(() => {
    if (ref.current) {
      const { isRight: _isRight } = checkElementPosition(ref.current);
      setIsRight(_isRight);
    }
  }, []);

  return (
    <div ref={ref}>
      {items.map((item: DropdownItemType, index: number) => {
        if (item.type === 'clickable') {
          return (
            <Clickable
              key={index}
              item={item}
              size={size}
            />
          );
        }
        if (item.type === 'subTrigger') {
          return (
            <SubTrigger
              key={index}
              item={item}
              size={size}
              subContentPlacement={isRight ? 'left' : 'right'}
            />
          );
        }
      })}
    </div>
  );
}
