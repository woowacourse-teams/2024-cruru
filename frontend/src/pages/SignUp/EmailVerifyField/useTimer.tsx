import { useEffect, useRef, useState } from 'react';

interface UseTimerProps {
  initValue: number;
  onEndTimer: () => void;
}

export default function useTimer({ initValue, onEndTimer }: UseTimerProps) {
  const [timer, setTimer] = useState(initValue);

  const timerRef = useRef<NodeJS.Timeout | null>(null);

  const endTimer = () => {
    if (timerRef.current) {
      clearInterval(timerRef.current);
      timerRef.current = null;
    }
  };

  const startTimer = () => {
    setTimer(initValue);
    if (timerRef.current) clearInterval(timerRef.current);

    timerRef.current = setInterval(() => {
      setTimer((prevTimer) => {
        if (prevTimer <= 1) {
          endTimer();
          onEndTimer();

          return 0;
        }
        return prevTimer - 1;
      });
    }, 1000);
  };

  // eslint-disable-next-line arrow-body-style
  useEffect(() => {
    return () => {
      if (timerRef.current) {
        clearInterval(timerRef.current);
      }
    };
  }, []);
  return { timer, startTimer, endTimer };
}
