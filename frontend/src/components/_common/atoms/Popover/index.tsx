import { PropsWithChildren, useEffect, useRef } from 'react';
import ReactDOM from 'react-dom';
import { PopoverProvider } from '@contexts/PopoverContext';
import checkElementPosition from '@utils/checkElementPosition';
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
      document.addEventListener('mouseup', handleClickOutside);
      document.addEventListener('scroll', onClose, true);
    }

    return () => {
      document.removeEventListener('mouseup', handleClickOutside);
      document.removeEventListener('scroll', onClose, true);
    };
  }, [isOpen, onClose, anchorEl]);

  useEffect(() => {
    if (isOpen && popoverRef.current && anchorEl) {
      const anchorRect = anchorEl.getBoundingClientRect();
      const { isRight, isBottom } = checkElementPosition(anchorEl);

      if (isBottom) {
        popoverRef.current.style.bottom = `${window.innerHeight - anchorRect.bottom + window.screenY}px`;
      } else {
        popoverRef.current.style.top = `${anchorRect.bottom + window.scrollY}px`;
      }

      if (isRight) {
        popoverRef.current.style.right = `${window.innerWidth - anchorRect.right + window.scrollX}px`;
      } else {
        popoverRef.current.style.left = `${anchorRect.left + window.scrollX}px`;
      }
    }
  }, [isOpen, anchorEl]);

  if (!isOpen) return null;

  return ReactDOM.createPortal(
    <PopoverProvider onClose={onClose}>
      <S.PopoverWrapper
        ref={popoverRef}
        role="dialog"
        aria-modal="true"
        onClick={(event: React.MouseEvent) => event.stopPropagation()}
      >
        {children}
      </S.PopoverWrapper>
    </PopoverProvider>,
    document.body,
  );
}
