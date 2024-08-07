import { http } from 'msw';

import { DASHBOARDS } from '@api/endPoint';
import { DashboardFormInfo } from '@customTypes/dashboard';

const dashboardHandlers = [
  http.post(DASHBOARDS, async ({ request }) => {
    const url = new URL(request.url);
    const clubId = url.searchParams.get('clubId');
    const body = (await request.json()) as DashboardFormInfo;

    if (!body.startDate || !body.endDate || !body.postingContent || !body.title || clubId) {
      return new Response(null, {
        status: 400,
        statusText: 'The request body is missing required information.',
      });
    }

    return new Response(null, {
      status: 201,
      statusText: 'Created',
    });
  }),
];

export default dashboardHandlers;
