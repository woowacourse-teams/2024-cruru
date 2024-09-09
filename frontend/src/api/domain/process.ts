import { ProcessResponse } from '@customTypes/process';
import { PROCESSES } from '../endPoint';
import { createParams } from '../utils';
import APIClient from '../APIClient';

const apiClient = new APIClient(PROCESSES);

const processApis = {
  get: async ({ dashboardId }: { dashboardId: string }): Promise<ProcessResponse> =>
    apiClient.get({
      path: `?${createParams({ dashboardId })}`,
    }),

  create: async (params: { dashboardId: number; orderIndex: number; name: string; description?: string }) =>
    apiClient.post({
      path: `?${createParams({ dashboardId: String(params.dashboardId) })}`,
      body: {
        orderIndex: params.orderIndex,
        processName: params.name,
        description: params?.description,
      },
    }),

  modify: async (params: { processId: number; name: string; description?: string }) =>
    apiClient.patch({
      path: `/${params.processId}`,
      body: {
        processName: params.name,
        description: params?.description,
      },
    }),

  delete: async ({ processId }: { processId: number }) =>
    apiClient.delete({
      path: `/${processId}`,
    }),
};

export default processApis;
