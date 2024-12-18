import { useState } from 'react';

/**
 * useLocalStorageState
 * @param key - LocalStorage에 저장될 키 값
 * @param initialValue - 초기 상태 값
 * @returns [상태 값, 상태를 변경하는 함수] useState의 반환값과 동일합니다.
 */
function useLocalStorageState<T>(key: string, initialValue: T): [T, (value: T | ((prev: T) => T)) => void] {
  const [state, _setState] = useState<T>(() => {
    try {
      const storedValue = window.localStorage.getItem(key);
      return storedValue !== null ? JSON.parse(storedValue) : initialValue;
    } catch (error) {
      return initialValue;
    }
  });

  const setState = (value: T | ((prev: T) => T)) => {
    try {
      const newState = value instanceof Function ? value(state) : value;
      _setState(value);
      window.localStorage.setItem(key, JSON.stringify(newState));
    } catch (error) {
      console.error(`"${key}":`, error);
    }
  };

  return [state, setState];
}

export default useLocalStorageState;
