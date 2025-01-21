import { PropsWithChildren, useEffect, useState } from 'react';
import { createPortal } from 'react-dom';

export default function TooltipPortal({ children }: PropsWithChildren) {
  const [portalElement, setPortalElement] = useState<HTMLElement | null>(null);

  useEffect(() => {
    let element = document.getElementById('tooltip-portal-container');

    if (!element) {
      element = document.createElement('div');
      element.id = 'tooltip-portal-container';
      document.body.appendChild(element);
    }

    setPortalElement(element);

    return () => {
      if (element && element.childNodes.length === 0) {
        element.remove();
      }
    };
  }, []);

  if (!portalElement) return null;

  return createPortal(children, portalElement);
}
