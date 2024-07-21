import { useState, useRef, useEffect } from 'react';
import S from './style';
import DropdownItem from '../DropdownItem';
import ChevronButton from '../ChevronButton';

type Item = {
  id: number;
  name: string;
  isHighlight?: boolean;
  onClick: () => void;
};

export interface DropdownProps {
  children?: string;
  size?: 'sm' | 'md';
  items: Item[];
}

export default function Dropdown({ children, size = 'sm', items }: DropdownProps) {
  const [selected, setSelected] = useState(children);
  const [isOpen, setIsOpen] = useState(false);
  const dropdownRef = useRef<HTMLDivElement>(null);

  const toggleDropdown = () => {
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

  return (
    <S.Container
      ref={dropdownRef}
      size={size}
      isOpen={isOpen}
    >
      <S.Header
        onClick={toggleDropdown}
        size={size}
        isOpen={isOpen}
      >
        {selected || 'Default'}
        <ChevronButton
          direction="down"
          size={size}
        />
      </S.Header>

      {isOpen && (
        <S.List size={size}>
          {items.map(({ name, isHighlight, id, onClick }) => (
            <DropdownItem
              onClick={() => {
                onClick();
                handleClickItem(name);
              }}
              key={id}
              item={name}
              isHighlight={isHighlight}
              size={size}
            />
          ))}
        </S.List>
      )}
    </S.Container>
  );
}
