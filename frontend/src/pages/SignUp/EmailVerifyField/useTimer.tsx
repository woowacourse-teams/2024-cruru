import { useRef, useState } from 'react';

interface UseTimerProps {
  initValue: number;
  onEndTimer: () => void;
}

export default function useTimer({ initValue, onEndTimer }: UseTimerProps) {
  const [timer, setTimer] = useState(initValue);
  const timerRef = useRef<NodeJS.Timeout | null>(null);
  const endTimeStamp = useRef<number | null>(null);

  const endTimer = () => {
    if (timerRef.current) {
      clearInterval(timerRef.current);
      timerRef.current = null;
    }
  };

  const startTimer = () => {
    setTimer(initValue);
    if (timerRef.current) clearInterval(timerRef.current);

    endTimeStamp.current = Date.now() + initValue * 1000;
    const interval = () => {
      if (!endTimeStamp.current) return;
      const elapsedTime = Math.round((endTimeStamp.current - Date.now()) / 1000);
      setTimer((prevTimer) => {
        if (prevTimer <= 1) {
          endTimer();
          onEndTimer();
          return 0;
        }
        return elapsedTime;
      });
    };
    timerRef.current = setInterval(interval, 1000);
  };

  return { timer, startTimer, endTimer };
}
