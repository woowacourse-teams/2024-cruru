import { PropsWithChildren } from 'react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { act, renderHook } from '@testing-library/react';
import useDashboardCreateForm from '.';

describe('useDashboardCreateForm', () => {
  const createWrapper = () => {
    const queryClient = new QueryClient();
    // eslint-disable-next-line react/function-component-definition
    return ({ children }: PropsWithChildren) => (
      <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>
    );
  };

  it('addQuestion을 호출하면 새로운 질문이 추가된다.', () => {
    const { result } = renderHook(() => useDashboardCreateForm(), { wrapper: createWrapper() });

    act(() => {
      result.current.addQuestion();
    });

    expect(result.current.applyState).toHaveLength(4);
  });

  it('setQuestionType으로 질문 타입을 변경할 수 있다.', () => {
    const { result } = renderHook(() => useDashboardCreateForm(), { wrapper: createWrapper() });

    act(() => {
      result.current.setQuestionType(0)('LONG_ANSWER');
    });

    expect(result.current.applyState[0].type).toBe('LONG_ANSWER');
  });

  it('setQuestionOptions로 질문 옵션을 설정할 수 있다.', () => {
    const { result } = renderHook(() => useDashboardCreateForm(), { wrapper: createWrapper() });

    const options = [{ value: 'Option 1' }, { value: 'Option 2' }];
    act(() => {
      result.current.setQuestionOptions(0)(options);
    });

    expect(result.current.applyState[0].choices).toEqual([
      { choice: 'Option 1', orderIndex: 0 },
      { choice: 'Option 2', orderIndex: 1 },
    ]);
  });

  it('인덱스가 1에서 3인 질문과 마지막 요소는 next할 수 없다.', () => {
    const { result } = renderHook(() => useDashboardCreateForm(), { wrapper: createWrapper() });
    const initialQuestions = result.current.applyState;

    act(() => {
      result.current.setQuestionNext(1)();
      result.current.setQuestionNext(2)();
      result.current.setQuestionNext(3)();
      result.current.setQuestionNext(initialQuestions.length - 1)();
    });

    expect(result.current.applyState).toEqual(initialQuestions);
  });

  it('인덱스가 1에서 4인 질문은 prev할 수 없다.', () => {
    const { result } = renderHook(() => useDashboardCreateForm(), { wrapper: createWrapper() });
    const initialQuestions = result.current.applyState;

    act(() => {
      result.current.setQuestionPrev(1)();
      result.current.setQuestionPrev(2)();
      result.current.setQuestionPrev(3)();
      result.current.setQuestionPrev(4)();
    });

    expect(result.current.applyState).toEqual(initialQuestions);
  });

  it('deleteQuestion을 호출하면 인덱스가 3 이상인 경우에만 해당 인덱스의 질문이 삭제된다.', () => {
    const { result } = renderHook(() => useDashboardCreateForm(), { wrapper: createWrapper() });

    // 인덱스가 3 미만인 경우 삭제되지 않아야 한다.
    act(() => {
      result.current.deleteQuestion(0);
      result.current.deleteQuestion(1);
      result.current.deleteQuestion(2);
    });

    expect(result.current.applyState).toHaveLength(3);

    // 인덱스가 3 이상인 경우 삭제되어야 한다.
    act(() => {
      result.current.addQuestion();
      result.current.deleteQuestion(3);
    });

    expect(result.current.applyState).toHaveLength(3);
  });
});
