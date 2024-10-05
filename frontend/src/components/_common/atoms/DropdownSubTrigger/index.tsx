import { PropsWithChildren, useRef, useState } from 'react';
import { HiChevronRight } from 'react-icons/hi';
import S from './style';

export interface DropdownSubTriggerProps extends PropsWithChildren {
  item: string;
  size: 'sm' | 'md';
  isHighlight?: boolean;
  placement?: 'left' | 'right';
  hasSeparate?: boolean;
}

export default function DropdownSubTrigger({
  item,
  size,
  isHighlight = false,
  hasSeparate = false,
  placement = 'right',
  children,
}: DropdownSubTriggerProps) {
  const [isSubOpen, setIsSubOpen] = useState(false);
  const ref = useRef<HTMLDivElement | null>(null);

  const handleMouseOver = (e: React.MouseEvent<HTMLDivElement>) => {
    e.stopPropagation();
    setIsSubOpen(true);
  };

  const handleMouseLeave = () => {
    setIsSubOpen(false);
  };

  return (
    <S.Item
      ref={ref}
      size={size}
      onMouseOver={handleMouseOver}
      onMouseLeave={handleMouseLeave}
      isHighlight={isHighlight}
      hasSeparate={hasSeparate}
    >
      {item}
      <HiChevronRight size={size === 'sm' ? 16 : 18} />
      {isSubOpen && (
        <S.SubItemBoundary
          placement={placement}
          width={ref.current?.offsetWidth || 0}
        >
          <S.SubItemContainer
            size={size}
            width={ref.current?.offsetWidth || 0}
          >
            {children}
          </S.SubItemContainer>
        </S.SubItemBoundary>
      )}
    </S.Item>
  );
}
