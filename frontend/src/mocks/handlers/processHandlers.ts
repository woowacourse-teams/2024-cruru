import { http, HttpResponse } from 'msw';

import { PROCESSES } from '@api/endPoint';
import processes from '../processMockData.json';

const processHandlers = [
  http.get(PROCESSES, () => HttpResponse.json(processes)),
  http.post(PROCESSES, async ({ request }) => {
    const body = (await request.json()) as {
      order_index: number;
      process_name: string;
      description?: string;
    };

    if (!body.order_index || !body.process_name) {
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
];

export default processHandlers;
