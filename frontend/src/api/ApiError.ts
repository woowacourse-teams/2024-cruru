class ApiError extends Error {
  public statusCode: number | null;

  public data: unknown;

  constructor({ message, statusCode, data }: { message: string; statusCode: number | null; data?: unknown }) {
    super(message);
    // TODO: name을 코드 별로 다르게 줄 수도 있음.
    // e.g. statusCode가 301일 경우 'RedirectError'
    this.name = 'ApiError';
    this.statusCode = statusCode;
    this.data = data;
  }
}

export default ApiError;
