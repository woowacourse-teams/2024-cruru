import { useCallback, useEffect, useRef, useState } from 'react';

export default function useElementRect() {
  const ref = useRef<HTMLDivElement>(null);
  const [rect, setRect] = useState<DOMRect | null>(null);

  const updateRect = useCallback(() => {
    if (ref.current) {
      setRect(ref.current.getBoundingClientRect());
    }
  }, []);

  useEffect(() => {
    const element = ref.current;
    if (!element) return;

    updateRect();

    const resizeObserver = new ResizeObserver(() => {
      updateRect();
    });

    resizeObserver.observe(element);

    return () => {
      resizeObserver.disconnect();
    };
  }, [updateRect]);

  return [ref, rect] as const;
}
