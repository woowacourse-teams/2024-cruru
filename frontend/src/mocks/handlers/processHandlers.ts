import { http, HttpResponse } from 'msw';

import { PROCESSES } from '@api/endPoint';
import processes from '../processMockData.json';

const processHandlers = [
  http.get(PROCESSES, () => HttpResponse.json(processes)),

  http.post(`${PROCESSES}`, async ({ request }) => {
    const url = new URL(request.url);
    const dashboardId = url.searchParams.get('dashboard_id');
    const body = (await request.json()) as {
      order_index: number;
      process_name: string;
      description?: string;
    };

    if (!body.order_index || !body.process_name || !dashboardId) {
      return new Response(null, {
        status: 404,
        statusText: 'Process Not Found',
      });
    }

    return new Response(null, {
      status: 201,
      statusText: 'Created',
    });
  }),

  http.delete(`${PROCESSES}/:process_id`, async ({ params }) => {
    if (!params.process_id) {
      return new Response(null, {
        status: 400,
        statusText: 'Process Not Found',
      });
    }

    return new Response(null, {
      status: 200,
      statusText: 'OK',
    });
  }),

  http.patch(`${PROCESSES}/:process_id`, async ({ request, params }) => {
    const body = (await request.json()) as {
      process_name: string;
      description?: string;
    };

    if (!body.process_name || !params.process_id) {
      return new Response(null, {
        status: 400,
        statusText: 'Process Not Found',
      });
    }

    return new Response(null, {
      status: 200,
      statusText: 'OK',
    });
  }),
];

export default processHandlers;
