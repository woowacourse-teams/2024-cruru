import { useState, useRef, useEffect } from 'react';
import { DropdownListItem } from '@customTypes/common';
import DropdownItem from '@components/_common/atoms/DropdownItem';
import { HiChevronDown } from 'react-icons/hi';
import S from './style';

export interface DropdownProps {
  initValue?: string;
  width?: number;
  size?: 'sm' | 'md';
  items: DropdownListItem[];
  isShadow?: boolean;
  disabled?: boolean;
}

export default function Dropdown({
  initValue,
  width,
  size = 'sm',
  items,
  isShadow = true,
  disabled = false,
}: DropdownProps) {
  const [selected, setSelected] = useState(initValue);
  const [isOpen, setIsOpen] = useState(false);
  const dropdownRef = useRef<HTMLDivElement>(null);

  const toggleDropdown = () => {
    if (disabled) return;
    setIsOpen(!isOpen);
  };

  const handleClickOutside = (event: MouseEvent) => {
    if (dropdownRef.current && !dropdownRef.current.contains(event.target as Node)) {
      setIsOpen(false);
    }
  };

  useEffect(() => {
    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  const handleClickItem = (item: string) => {
    setSelected(item);
    setIsOpen(false);
  };

  const sizeObj: Record<typeof size, number> = {
    sm: 24,
    md: 36,
  };

  return (
    <S.Container
      ref={dropdownRef}
      size={size}
      isOpen={isOpen}
      width={width}
      isShadow={isShadow}
      disabled={disabled}
    >
      <S.Header
        onClick={toggleDropdown}
        size={size}
        isOpen={isOpen}
      >
        {selected || 'Default'}
        <HiChevronDown size={sizeObj[size]} />
      </S.Header>

      {isOpen && (
        <S.List
          size={size}
          isShadow={isShadow}
        >
          {items.map(({ name, isHighlight, id, hasSeparate, onClick }) => (
            <DropdownItem
              onClick={() => {
                onClick({ targetProcessId: id });
                handleClickItem(name);
              }}
              key={id}
              item={name}
              isHighlight={isHighlight}
              size={size}
              hasSeparate={hasSeparate}
            />
          ))}
        </S.List>
      )}
    </S.Container>
  );
}
