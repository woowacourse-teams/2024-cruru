import { PropsWithChildren } from 'react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { act, renderHook } from '@testing-library/react';
import ToastProvider from '@contexts/ToastContext';
import useDashboardCreateForm from '.';

jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useNavigate: jest.fn(),
}));

describe('useDashboardCreateForm', () => {
  beforeEach(() => {
    localStorage.setItem('clubId', '1');
  });

  afterEach(() => {
    localStorage.clear();
    jest.restoreAllMocks();
  });

  const createWrapper = () => {
    const queryClient = new QueryClient();
    // eslint-disable-next-line react/function-component-definition
    return ({ children }: PropsWithChildren) => (
      <ToastProvider>
        <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>
      </ToastProvider>
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

    const choices = [{ choice: 'Option 1' }, { choice: 'Option 2' }];
    act(() => {
      result.current.setQuestionOptions(0)(choices);
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

  it('인덱스가 0에서 3인 질문은 prev할 수 없다.', () => {
    const { result } = renderHook(() => useDashboardCreateForm(), { wrapper: createWrapper() });

    act(() => result.current.addQuestion());
    const expectQuestions = result.current.applyState;

    act(() => {
      result.current.setQuestionPrev(0)();
      result.current.setQuestionPrev(1)();
      result.current.setQuestionPrev(2)();
      result.current.setQuestionPrev(3)();
    });

    expect(result.current.applyState).toEqual(expectQuestions);
  });

  it('deleteQuestion을 호출하면 인덱스가 3 이상인 경우에만 해당 인덱스의 질문이 삭제된다.', async () => {
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

  it('localStorage에 값이 있으면 confirm을 1번 띄운다.', () => {
    jest.spyOn(window, 'confirm').mockImplementation(() => true);

    // Arrange
    localStorage.setItem('1-step', 'recruitmentForm');
    localStorage.setItem(
      '1-info',
      JSON.stringify({
        startDate: '2024-12-01',
        endDate: '2024-12-31',
        title: 'Test Title',
        postingContent: 'This is a test content',
      }),
    );

    const { result } = renderHook(() => useDashboardCreateForm(), { wrapper: createWrapper() });

    // Act
    expect(window.confirm).toHaveBeenCalledTimes(1);

    // Assert
    expect(result.current.stepState).toBe('recruitmentForm');
    expect(result.current.recruitmentInfoState).toEqual({
      startDate: '2024-12-01',
      endDate: '2024-12-31',
      title: 'Test Title',
      postingContent: 'This is a test content',
    });
  });

  it('필수 정보를 채운 후 새로고침 시 localStorage를 복원하고 공고를 성공적으로 생성한다.', async () => {
    jest.spyOn(window, 'confirm').mockImplementation(() => true);

    const { result, rerender } = renderHook(() => useDashboardCreateForm(), { wrapper: createWrapper() });

    // [1]Act
    act(() => {
      result.current.setRecruitmentInfoState((prev) => ({
        ...prev,
        startDate: '2024-12-01',
        endDate: '2024-12-31',
        title: 'Test Title',
        postingContent: 'This is a test content.',
      }));
      result.current.nextStep();
      result.current.addQuestion();
    });
    act(() => result.current.setQuestionTitle(3)('Test Question'));

    // [1]Assert
    expect(result.current.stepState).toBe('applyForm');

    // [2]Arrange
    rerender(); // 새로고침과 같은 역할

    expect(result.current.stepState).toBe('applyForm');
    expect(result.current.recruitmentInfoState).toEqual({
      startDate: '2024-12-01',
      endDate: '2024-12-31',
      title: 'Test Title',
      postingContent: 'This is a test content.',
    });

    // [2]Act
    await act(async () => {
      result.current.nextStep();
    });

    // [2]Assert
    expect(result.current.submitMutator.isSuccess).toBe(true);
  });

  it('confirm 입력으로 false를 입력하면 localStorage가 비워진다.', () => {
    jest.spyOn(window, 'confirm').mockImplementation(() => false);

    localStorage.setItem('1-step', 'recruitmentForm');
    localStorage.setItem(
      '1-info',
      JSON.stringify({
        startDate: '2024-12-01',
        endDate: '2024-12-31',
        title: 'Test Title',
        postingContent: 'This is a test content',
      }),
    );
    localStorage.setItem('1-apply', JSON.stringify([{ question: 'Test Question', type: 'SHORT_ANSWER' }]));

    renderHook(() => useDashboardCreateForm(), { wrapper: createWrapper() });

    expect(localStorage.getItem('1-step')).toBeNull();
    expect(localStorage.getItem('1-info')).toBeNull();
    expect(localStorage.getItem('1-apply')).toBeNull();
  });
});
