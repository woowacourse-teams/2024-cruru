import { DashboardFormInfo } from '@customTypes/dashboard';
import { DASHBOARDS } from './endPoint';
import { convertParamsToQueryString } from './utils';

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
      throw new Error('Network response was not ok');
    }

    return response;
  },
};

export default dashboardApis;
