import { DashboardFormInfo } from '@customTypes/dashboard';
import { DASHBOARDS } from './endPoint';
import { convertParamsToQueryString } from './utils';
import ApiError from './ApiError';

const dashboardApis = {
  get: async ({ dashboardId }: { dashboardId: string }) => {
    const queryParams = {
      clubId: dashboardId,
    };

    const response = await fetch(`${DASHBOARDS}?${convertParamsToQueryString(queryParams)}`, {
      headers: {
        Accept: 'application/json',
      },
    });

    if (!response.ok) {
      throw new ApiError({
        method: 'GET',
        statusCode: response.status,
        message: '공고 리스트의 정보를 불러오지 못했습니다.',
      });
    }

    const data = await response.json();

    return data;
  },

  create: async ({ clubId, dashboardFormInfo }: { clubId: string; dashboardFormInfo: DashboardFormInfo }) => {
    const queryParams = {
      clubId,
    };

    const response = await fetch(`${DASHBOARDS}?${convertParamsToQueryString(queryParams)}`, {
      method: 'POST',
      headers: {
        'Content-type': 'application/json',
      },
      body: JSON.stringify(dashboardFormInfo),
    });

    if (!response.ok) {
      throw new ApiError({ message: 'Network response was not ok', method: 'POST', statusCode: response.status });
    }

    return response;
  },
};

export default dashboardApis;
