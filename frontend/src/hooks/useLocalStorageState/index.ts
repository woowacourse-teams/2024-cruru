import { useState } from 'react';

interface OptionProp {
  enableStorage: boolean;
}

/**
 * useLocalStorageState
 * @param key - LocalStorage에 저장될 키 값
 * @param initialValue - 초기 상태 값
 * @param option - { enableStorage: boolean }
 * @returns [상태 값, 상태를 변경하는 함수] useState의 반환값과 동일합니다.
 */
function useLocalStorageState<T>(
  key: string,
  initialValue: T,
  option: OptionProp = { enableStorage: true },
): [T, (value: T | ((prev: T) => T)) => void] {
  const [state, _setState] = useState<T>(() => {
    if (!option.enableStorage) return initialValue;

    try {
      const storedValue = window.localStorage.getItem(key);
      return storedValue !== null ? JSON.parse(storedValue) : initialValue;
    } catch (error) {
      return initialValue;
    }
  });

  const saveToLocalStorage = (value: T) => {
    try {
      window.localStorage.setItem(key, JSON.stringify(value));
    } catch (error) {
      console.error(`"${key}":`, error);
    }
  };

  const setState = (value: T | ((prev: T) => T)) => {
    _setState(value);
    saveToLocalStorage(value instanceof Function ? value(state) : value);
  };

  return [state, setState];
}

export default useLocalStorageState;
