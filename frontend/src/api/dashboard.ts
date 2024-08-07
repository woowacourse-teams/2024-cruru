import { DashboardFormInfo } from '@customTypes/dashboard';
import { DASHBOARDS } from './endPoint';
import { convertParamsToQueryString } from './utils';
import ApiError from './ApiError';

const dashboardApis = {
  create: async ({ clubId, dashboardFormInfo }: { clubId: number; dashboardFormInfo: DashboardFormInfo }) => {
    const queryParams = {
      clubId: String(clubId),
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
