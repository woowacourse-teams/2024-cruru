/* eslint-disable react/function-component-definition */
/* eslint-disable react/jsx-one-expression-per-line */
/* eslint-disable react/button-has-type */
import { render, fireEvent, screen } from '@testing-library/react';
import useLocalStorageState from '.';
import '@testing-library/jest-dom';

// 1. 원시값을 테스트하는 컴포넌트
const PrimitiveValueComponent = () => {
  const [value, setValue] = useLocalStorageState<number>('primitiveKey', 0);

  return (
    <div>
      <p>Value: {value}</p>
      <button onClick={() => setValue(value + 1)}>Increment</button>
      <button onClick={() => setValue(0)}>Reset</button>
    </div>
  );
};

// 2. 객체를 테스트하는 컴포넌트
const ObjectValueComponent = () => {
  const [user, setUser] = useLocalStorageState<{ name: string; age: number }>('objectKey', {
    name: 'lurgi',
    age: 30,
  });

  return (
    <div>
      <p>
        Name: {user.name}, Age: {user.age}
      </p>
      <button onClick={() => setUser({ ...user, age: user.age + 1 })}>Increase Age</button>
    </div>
  );
};

describe('useLocalStorageState의 값이 원시값인 경우에 대한 테스트', () => {
  beforeEach(() => {
    window.localStorage.clear();
  });

  // 1. 원시값 테스트
  describe('Primitive Value Tests', () => {
    it('초기 상태를 설정한다', () => {
      render(<PrimitiveValueComponent />);
      expect(screen.getByText('Value: 0')).toBeInTheDocument();
    });

    it('상태 변경 시 localStorage에 저장한다', () => {
      render(<PrimitiveValueComponent />);
      fireEvent.click(screen.getByText('Increment'));
      expect(screen.getByText('Value: 1')).toBeInTheDocument();
      expect(window.localStorage.getItem('primitiveKey')).toBe('1');
    });

    it('localStorage에 값이 있으면 초기 상태로 사용한다', () => {
      window.localStorage.setItem('primitiveKey', '10');
      render(<PrimitiveValueComponent />);
      expect(screen.getByText('Value: 10')).toBeInTheDocument();
    });
  });

  describe('useLocalStorageState의 값이 객체인 경우에 대한 테스트', () => {
    it('초기 상태를 설정한다', () => {
      render(<ObjectValueComponent />);
      expect(screen.getByText('Name: lurgi, Age: 30')).toBeInTheDocument();
    });

    it('상태 변경 시 localStorage에 저장한다', () => {
      render(<ObjectValueComponent />);
      fireEvent.click(screen.getByText('Increase Age'));
      expect(screen.getByText('Name: lurgi, Age: 31')).toBeInTheDocument();

      const storedValue = window.localStorage.getItem('objectKey');
      expect(storedValue).toBe(JSON.stringify({ name: 'lurgi', age: 31 }));
    });

    it('localStorage에 값이 있으면 초기 상태로 사용한다', () => {
      const storedObject = JSON.stringify({ name: 'Jane Doe', age: 28 });
      window.localStorage.setItem('objectKey', storedObject);

      render(<ObjectValueComponent />);
      expect(screen.getByText('Name: Jane Doe, Age: 28')).toBeInTheDocument();
    });
  });
});
