import { PropsWithChildren, useEffect, useRef } from 'react';
import ReactDOM from 'react-dom';
import { PopoverProvider } from '@contexts/PopoverContext';
import S from './style';

interface PopoverProps extends PropsWithChildren {
  isOpen: boolean;
  onClose: () => void;
  anchorEl: HTMLElement | null;
}

export default function Popover({ isOpen, onClose, anchorEl, children }: PopoverProps) {
  const popoverRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (popoverRef.current && !popoverRef.current.contains(event.target as Node) && event.target !== anchorEl) {
        onClose();
      }
    };

    if (isOpen) {
      document.addEventListener('mousedown', handleClickOutside);
    }

    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [isOpen, onClose, anchorEl]);

  useEffect(() => {
    if (isOpen && popoverRef.current && anchorEl) {
      const anchorRect = anchorEl.getBoundingClientRect();
      popoverRef.current.style.top = `${anchorRect.bottom + window.scrollY}px`;
      popoverRef.current.style.left = `${anchorRect.left + window.scrollX}px`;
    }
  }, [isOpen, anchorEl]);

  if (!isOpen) return null;

  return ReactDOM.createPortal(
    <PopoverProvider onClose={onClose}>
      <S.PopoverWrapper
        ref={popoverRef}
        role="dialog"
        aria-modal="true"
      >
        {children}
      </S.PopoverWrapper>
    </PopoverProvider>,
    document.body,
  );
}
