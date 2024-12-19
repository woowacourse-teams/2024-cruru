import { useState } from 'react';

interface OptionProp {
  key: string;
  enableStorage?: boolean;
}

/**
 * useLocalStorageState
 * @param initialValue - 초기 상태 값
 * @param option - { key: LocalStorage에 저장될 키 값, enableStorage: LocalStorage의 값을 사용할지 여부}
 * @returns [상태 값, 상태를 변경하는 함수] useState의 반환값과 동일합니다.
 */
function useLocalStorageState<T>(initialValue: T, option: OptionProp): [T, (value: T | ((prev: T) => T)) => void] {
  const { key, enableStorage = true } = option;

  const [state, _setState] = useState<T>(() => {
    if (!enableStorage) return initialValue;

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
