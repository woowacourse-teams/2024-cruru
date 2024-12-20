import { useState } from 'react';

interface OptionProp {
  key: string;
  enableStorage?: boolean;
}

const safeParseJSON = <T>(value: string | null, fallback: T): T => {
  try {
    return value !== null ? JSON.parse(value) : fallback;
  } catch (error) {
    if (process.env.NODE_ENV === 'development') {
      console.error('JSON 파싱 실패:', error);
    }
    return fallback;
  }
};

const safeStringifyJSON = <T>(value: T): string | null => {
  try {
    return JSON.stringify(value);
  } catch (error) {
    if (process.env.NODE_ENV === 'development') {
      console.error('JSON 직렬화 실패:', error);
    }
    return null;
  }
};

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

    const storedValue = window.localStorage.getItem(key);
    return safeParseJSON(storedValue, initialValue);
  });

  const saveToLocalStorage = (value: T) => {
    const stringifiedValue = safeStringifyJSON(value);
    if (stringifiedValue !== null) {
      window.localStorage.setItem(key, stringifiedValue);
    }
  };

  const setState = (value: T | ((prev: T) => T)) => {
    _setState(value);
    saveToLocalStorage(value instanceof Function ? value(state) : value);
  };

  return [state, setState];
}

export default useLocalStorageState;
