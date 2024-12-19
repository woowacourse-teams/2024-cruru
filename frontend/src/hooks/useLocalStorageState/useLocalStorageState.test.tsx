import { act } from 'react';
import { renderHook } from '@testing-library/react';
import useLocalStorageState from '.';

describe('useLocalStorageState의 값이 원시값인 경우에 대한 테스트', () => {
  beforeEach(() => {
    window.localStorage.clear();
  });

  describe('Primitive Value Tests', () => {
    it('초기 상태를 설정한다', () => {
      const { result } = renderHook(() => useLocalStorageState<number>('primitiveKey', 0));
      expect(result.current[0]).toBe(0);
    });

    it('[setState 인자 원시값] 상태 변경 시 localStorage에 저장한다', () => {
      const { result } = renderHook(() => useLocalStorageState<number>('primitiveKey', 0));

      act(() => {
        result.current[1]((prev) => prev + 1);
      });

      expect(result.current[0]).toBe(1);
      expect(window.localStorage.getItem('primitiveKey')).toBe('1');
    });

    it('[setState 인자 함수] 상태 변경 시 localStorage에 저장한다', () => {
      const { result } = renderHook(() => useLocalStorageState<number>('primitiveKey', 0));

      act(() => {
        result.current[1]((prev) => prev + 1);
      });

      expect(result.current[0]).toBe(1);
      expect(window.localStorage.getItem('primitiveKey')).toBe('1');
    });

    it('localStorage에 값이 있으면 초기 상태로 사용한다', () => {
      window.localStorage.setItem('primitiveKey', '10');

      const { result } = renderHook(() => useLocalStorageState<number>('primitiveKey', 0));
      expect(result.current[0]).toBe(10);

      act(() => {
        result.current[1]((prev) => prev + 1);
      });

      expect(result.current[0]).toBe(11);
      expect(window.localStorage.getItem('primitiveKey')).toBe('11');
    });

    it('enableStorage 옵션을 false로 지정한 경우 localStorage를 사용하지 않는다', () => {
      window.localStorage.setItem('primitiveKey', '10');

      const { result } = renderHook(() => useLocalStorageState<number>('primitiveKey', 0, { enableStorage: false }));

      expect(result.current[0]).toBe(0);
      expect(window.localStorage.getItem('primitiveKey')).toBe('10');
    });
  });

  describe('useLocalStorageState의 값이 객체인 경우에 대한 테스트', () => {
    it('초기 상태를 설정한다', () => {
      const initialObject = { name: 'lurgi', age: 30 };
      const { result } = renderHook(() => useLocalStorageState('objectKey', initialObject));
      expect(result.current[0]).toEqual(initialObject);
    });

    it('[setState 인자 원시값] 상태 변경 시 localStorage에 저장한다', () => {
      const initialObject = { name: 'lurgi', age: 30 };
      const { result } = renderHook(() => useLocalStorageState('objectKey', initialObject));

      act(() => {
        result.current[1]({ name: 'lurgi', age: 31 });
      });

      expect(result.current[0]).toEqual({ name: 'lurgi', age: 31 });
      expect(window.localStorage.getItem('objectKey')).toBe(JSON.stringify({ name: 'lurgi', age: 31 }));
    });

    it('[setState 인자 함수] 상태 변경 시 localStorage에 저장한다', () => {
      const initialObject = { name: 'lurgi', age: 30 };
      const { result } = renderHook(() => useLocalStorageState('objectKey', initialObject));

      act(() => {
        result.current[1]((prev) => ({ ...prev, age: prev.age + 1 }));
      });

      expect(result.current[0]).toEqual({ name: 'lurgi', age: 31 });
      expect(window.localStorage.getItem('objectKey')).toBe(JSON.stringify({ name: 'lurgi', age: 31 }));
    });

    it('localStorage에 값이 있으면 초기 상태로 사용한다', () => {
      const storedObject = JSON.stringify({ name: 'jeong woo', age: 28 });
      window.localStorage.setItem('objectKey', storedObject);

      const { result } = renderHook(() => useLocalStorageState('objectKey', { name: 'default', age: 0 }));
      expect(result.current[0]).toEqual({ name: 'jeong woo', age: 28 });

      act(() => {
        result.current[1]((prev) => ({ ...prev, age: prev.age + 1 }));
      });

      expect(result.current[0]).toEqual({ name: 'jeong woo', age: 29 });
      expect(window.localStorage.getItem('objectKey')).toBe(JSON.stringify({ name: 'jeong woo', age: 29 }));
    });

    it('enableStorage 옵션을 false로 지정한 경우 localStorage를 사용하지 않는다', () => {
      const storedObject = JSON.stringify({ name: 'jeong woo', age: 28 });
      window.localStorage.setItem('objectKey', storedObject);

      const initialObject = { name: 'lurgi', age: 30 };
      const { result } = renderHook(() => useLocalStorageState('objectKey', initialObject, { enableStorage: false }));

      expect(result.current[0]).toEqual(initialObject);
      expect(window.localStorage.getItem('objectKey')).toBe(storedObject);
    });
  });
});
