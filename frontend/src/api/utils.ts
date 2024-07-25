export const createParams = (params: Record<string, string>) => new URLSearchParams(params).toString();

export const convertParamsToQueryString = (params: Record<string, string>): string =>
  Object.keys(params)
    .map((key) => `${encodeURIComponent(key)}=${encodeURIComponent(params[key])}`)
    .join('&');
