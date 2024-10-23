import { useEffect, useRef, useState } from 'react';

interface UseTimerProps {
  initValue: number;
  onEndTimer: () => void;
}

export default function useTimer({ initValue, onEndTimer }: UseTimerProps) {
  const [timer, setTimer] = useState(initValue);
  const timerRef = useRef<NodeJS.Timeout | null>(null);
  const startTimestamp = useRef<number>(performance.now());

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
      const elapsedTime = Math.floor((performance.now() - startTimestamp.current) / 1000);
      setTimer((prevTimer) => {
        if (prevTimer <= 1) {
          endTimer();
          onEndTimer();

          return 0;
        }
        return initValue - elapsedTime;
      });
    }, 1000);
  };

  useEffect(() => {
    const handleVisibilityChange = () => {
      if (document.visibilityState === 'visible' && timerRef.current) {
        const elapsedTime = Math.floor((performance.now() - startTimestamp.current) / 1000);
        setTimer(initValue - elapsedTime);
      }
    };

    document.addEventListener('visibilitychange', handleVisibilityChange);

    return () => {
      document.removeEventListener('visibilitychange', handleVisibilityChange);
      if (timerRef.current) {
        clearInterval(timerRef.current);
      }
    };
  }, [initValue]);
  return { timer, startTimer, endTimer };
}
