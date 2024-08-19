import { Club, DashboardFormInfo } from '@customTypes/dashboard';
import { DASHBOARDS } from '../endPoint';
import { convertParamsToQueryString } from '../utils';
import APIClient from '../APIClient';

const apiClient = new APIClient(DASHBOARDS);

const dashboardApis = {
  get: async ({ dashboardId }: { dashboardId: string }) => {
    const queryParams = {
      clubId: dashboardId,
    };

    return apiClient.get<Club>({
      path: `?${convertParamsToQueryString(queryParams)}`,
    });
  },

  create: async ({ clubId, dashboardFormInfo }: { clubId: string; dashboardFormInfo: DashboardFormInfo }) =>
    apiClient.post<{
      postUrl: string;
      postId: string;
    }>({
      path: `?${convertParamsToQueryString({ clubId })}`,
      body: dashboardFormInfo,
    }),
};

export default dashboardApis;
