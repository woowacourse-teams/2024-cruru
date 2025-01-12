export function createExecutionTracker() {
  let hasExecuted = false;

  return {
    /**
     * 초기 실행 여부를 확인하고, 상태를 갱신합니다.
     * - 처음 호출 시 true를 반환하고, 이후 호출 시 false를 반환합니다.
     */
    executeIfFirst: () => {
      if (!hasExecuted) {
        hasExecuted = true;
        return true;
      }
      return false;
    },
  };
}
