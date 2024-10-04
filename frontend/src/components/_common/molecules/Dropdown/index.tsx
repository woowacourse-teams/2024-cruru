/* eslint-disable no-shadow */
import { useRef, useEffect, PropsWithChildren, useCallback } from 'react';
import { HiChevronDown } from 'react-icons/hi';
import { DropdownProvider, useDropdown } from '@contexts/DropdownContext';
import S from './style';

interface DropdownBaseProps extends PropsWithChildren {
  width?: number;
  size?: 'sm' | 'md';
  isShadow?: boolean;
  disabled?: boolean;
}

function DropdownBase({ width, size = 'sm', isShadow = true, disabled = false, children }: DropdownBaseProps) {
  const { isOpen, open, close, selected } = useDropdown();
  const dropdownRef = useRef<HTMLDivElement>(null);

  const toggleDropdown = () => {
    if (disabled) return;
    if (isOpen) close();
    if (!isOpen) open();
  };

  const handleClickOutside = useCallback(
    (event: MouseEvent) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target as Node)) {
        close();
      }
    },
    [close],
  );

  useEffect(() => {
    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [handleClickOutside]);

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
          {children}
        </S.List>
      )}
    </S.Container>
  );
}

export interface DropdownProps extends DropdownBaseProps {
  initValue: string;
}

export default function Dropdown({ initValue, ...props }: DropdownProps) {
  return (
    <DropdownProvider initValue={initValue}>
      <DropdownBase {...props} />
    </DropdownProvider>
  );
}
