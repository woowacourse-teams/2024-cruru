import { getTimeStatus } from './compareTime';

describe('getTimeStatus 테스트', () => {
  beforeEach(() => {
    // 모든 테스트에서 시스템 시간을 동일한 날짜로 고정합니다.
    const mockCurrentDate = new Date('2024-08-21T00:00:00Z');
    jest.useFakeTimers();
    jest.setSystemTime(mockCurrentDate);
  });

  afterEach(() => {
    // 테스트 후 원래의 시스템 시간으로 복원합니다.
    jest.useRealTimers();
  });

  it('현재 날짜가 시작 날짜 이전이면 Pending을 반환해야 한다', () => {
    const result = getTimeStatus({
      startDate: '2024-08-22T00:00:00Z',
      endDate: '2024-08-25T00:00:00Z',
    });

    expect(result.status).toBe('Pending');
    expect(result.isPending).toBe(true);
    expect(result.isOngoing).toBe(false);
    expect(result.isClosed).toBe(false);
  });

  it('현재 날짜가 시작일과 종료일 사이에 있으면 Ongoing를 반환해야 한다', () => {
    const result = getTimeStatus({
      startDate: '2024-08-20T00:00:00Z',
      endDate: '2024-08-23T00:00:00Z',
    });

    expect(result.status).toBe('Ongoing');
    expect(result.isPending).toBe(false);
    expect(result.isOngoing).toBe(true);
    expect(result.isClosed).toBe(false);
  });

  it('현재 날짜가 종료 날짜 이후이면 Closed를 반환해야 한다', () => {
    const result = getTimeStatus({
      startDate: '2024-08-18T00:00:00Z',
      endDate: '2024-08-20T00:00:00Z',
    });

    expect(result.status).toBe('Closed');
    expect(result.isPending).toBe(false);
    expect(result.isOngoing).toBe(false);
    expect(result.isClosed).toBe(true);
  });

  it('현재 날짜가 종료 날짜 이후이면 Closed를 반환해야 한다', () => {
    const result = getTimeStatus({
      startDate: '2024-08-21T00:00:00Z',
      endDate: '2024-08-21T00:00:00Z',
    });

    expect(result.status).toBe('Ongoing');
    expect(result.isPending).toBe(false);
    expect(result.isOngoing).toBe(true);
    expect(result.isClosed).toBe(false);
  });
});
