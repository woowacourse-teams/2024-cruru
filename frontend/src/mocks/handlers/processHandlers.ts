import { http, HttpResponse } from 'msw';

import { PROCESSES } from '@api/endPoint';
import processes from '../processMockData.json';

const processHandlers = [
  http.get(PROCESSES, () => HttpResponse.json(processes)),

  http.post(`${PROCESSES}`, async ({ request }) => {
    const url = new URL(request.url);
    const dashboardId = url.searchParams.get('dashboardId');
    const body = (await request.json()) as {
      orderIndex: number;
      processName: string;
      description?: string;
    };

    if (!body.orderIndex || !body.processName || !dashboardId) {
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

  http.delete(`${PROCESSES}/:processId`, async ({ params }) => {
    if (!params.processId) {
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

  http.patch(`${PROCESSES}/:processId`, async ({ request, params }) => {
    const body = (await request.json()) as {
      processName: string;
      description?: string;
    };

    if (!body.processName || !params.processId) {
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
