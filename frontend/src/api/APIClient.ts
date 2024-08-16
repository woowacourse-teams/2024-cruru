/* eslint-disable class-methods-use-this */
/* eslint-disable @typescript-eslint/no-explicit-any */

import ApiError from './ApiError';

type Method = 'GET' | 'POST' | 'PUT' | 'DELETE' | 'PATCH';

type BodyHashMap = Record<string, any>;

interface BaseAPIClientParams {
  path: string;
  hasCookies?: boolean;
}

interface APIClientType {
  get<T>(
    params: BaseAPIClientParams & {
      queryParams?: BodyHashMap;
    },
  ): Promise<T>;
  post<T>(params: BaseAPIClientParams & { body?: BodyHashMap }): Promise<T>;
  patch<T>(params: BaseAPIClientParams & { body?: BodyHashMap }): Promise<T>;
  put<T>(params: BaseAPIClientParams & { body?: BodyHashMap }): Promise<T>;
  delete(params: BaseAPIClientParams): Promise<void>;
}

export default class APIClient implements APIClientType {
  private baseURL: URL;

  constructor(baseURL: string) {
    this.baseURL = new URL(baseURL);
  }

  async get<T>(
    params: BaseAPIClientParams & {
      queryParams?: BodyHashMap;
    },
  ): Promise<T> {
    return this.request<T>({ method: 'GET', ...params });
  }

  async post<T>(params: BaseAPIClientParams & { body?: BodyHashMap }): Promise<T> {
    return this.request<T>({ method: 'POST', ...params });
  }

  async patch<T>(params: BaseAPIClientParams & { body?: BodyHashMap }): Promise<T> {
    return this.request<T>({ method: 'PATCH', ...params });
  }

  async put<T>(params: BaseAPIClientParams & { body?: BodyHashMap }): Promise<T> {
    return this.request<T>({ method: 'PUT', ...params });
  }

  async delete(params: BaseAPIClientParams): Promise<void> {
    return this.request<void>({ method: 'DELETE', ...params });
  }

  private getRequestInit({
    method,
    body,
    hasCookies = true,
  }: {
    method: Method;
    body?: BodyHashMap;
    hasCookies?: boolean;
  }) {
    const headers: HeadersInit = {
      Accept: 'application/json',
    };

    const requestInit: RequestInit = {
      method,
      credentials: hasCookies ? 'include' : 'omit',
      headers,
    };

    if (['POST', 'PUT', 'PATCH'].includes(method)) {
      headers['Content-Type'] = 'application/json';
    }

    if (body) {
      requestInit.body = JSON.stringify(body);
    }

    return requestInit;
  }

  private async request<T>({
    path,
    method,
    body,
    hasCookies,
  }: {
    path: string;
    method: Method;
    body?: BodyHashMap;
    queryParams?: BodyHashMap;
    hasCookies?: boolean;
  }): Promise<T> {
    const url = this.baseURL + path;
    const response = await fetch(url, this.getRequestInit({ method, body, hasCookies }));

    if (!response.ok) {
      const { status, statusText } = response;
      let errorMessage = `API통신에 실패했습니다: ${statusText}`;
      // response가 JSON형식으로 응답을 주지 않을 경우를 대비하여 try - catch를 사용합니다.
      try {
        const errorData = await response.json();
        errorMessage += ` - ${errorData.message}`;
      } catch {
        // JSON 파싱이 실패하면 기본 메시지만 사용합니다.
      }
      throw new ApiError({ message: errorMessage, statusCode: status, method });
    }

    // Content-Type 확인 후 응답 처리
    const contentType = response.headers.get('Content-Type');
    if (contentType?.includes('application/json')) {
      return response.json();
    }

    // JSON이 아닌 응답을 다루기 위해 다른 처리 추가
    const textData = await response.text();
    return textData as unknown as T;
  }
}
