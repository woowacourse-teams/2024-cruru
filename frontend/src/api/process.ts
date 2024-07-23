import snakeToCamel from '@utils/snakeToCamel';
import { PROCESSES } from './endPoint';
import { createParams } from './utils';

const processApis = {
  get: async ({ id }: { id: number }) => {
    const response = await fetch(`${PROCESSES}?${createParams({ dashboard_id: String(id) })}`, {
      headers: {
        Accept: 'application/json',
      },
    });

    if (!response.ok) {
      throw new Error('Network response was not ok');
    }

    const data = await response.json();
    return snakeToCamel(data);
  },

  create: async (params: { dashboardId: number; orderIndex: number; name: string; description?: string }) => {
    const response = await fetch(`${PROCESSES}?${createParams({ dashboard_id: String(params.dashboardId) })}`, {
      method: 'POST',
      headers: {
        Accept: 'application/json',
      },
      body: JSON.stringify({
        order_index: params.orderIndex,
        process_name: params.name,
        description: params?.description,
      }),
    });

    if (!response.ok) {
      throw new Error('Network response was not ok');
    }

    return response;
  },
};

export default processApis;
