import { http, HttpResponse } from 'msw';

import { DASHBOARDS } from '@api/endPoint';
import { DashboardFormInfo } from '@customTypes/dashboard';
import DASHBOARD_LIST from '../dashboardList.json';

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

    const responseBody = JSON.stringify({
      postId: 1,
      dashboardId: 1,
    });

    return new Response(responseBody, {
      status: 201,
      statusText: 'Created',
      headers: {
        'Content-Type': 'application/json',
      },
    });
  }),

  http.get(DASHBOARDS, ({ request }) => {
    const url = new URL(request.url);
    const clubId = url.searchParams.get('clubId');

    if (!clubId) {
      return new Response(null, {
        status: 400,
        statusText: 'The request Param is missing required information.',
      });
    }

    return HttpResponse.json(DASHBOARD_LIST);
  }),
];

export default dashboardHandlers;
