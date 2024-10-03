import { useState, useRef, useEffect, PropsWithChildren, isValidElement, cloneElement, Children } from 'react';
import { HiChevronDown } from 'react-icons/hi';
import S from './style';

export interface DropdownProps extends PropsWithChildren {
  initValue?: string;
  width?: number;
  size?: 'sm' | 'md';
  isShadow?: boolean;
  disabled?: boolean;
}

export default function Dropdown({
  initValue,
  width,
  size = 'sm',
  isShadow = true,
  disabled = false,
  children,
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

  const cloneChild = (child: React.ReactNode) =>
    isValidElement(child)
      ? // eslint-disable-next-line prettier/prettier
        cloneElement(
          child,
          child.props.onClick && {
            onClick: handleClickItem,
          },
        )
      : child;

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
          {Children.map(children, cloneChild)}
        </S.List>
      )}
    </S.Container>
  );
}
