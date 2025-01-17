export function createExecutionTracker() {
  let hasExecuted = false;

  return {
    executet: () => {
      hasExecuted = true;
    },
    getHasExecuted: () => hasExecuted,
  };
}
