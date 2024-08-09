/* eslint-disable camelcase */
import { http, HttpResponse } from 'msw';

import { EVALUATIONS } from '@api/endPoint';
import evaluationMockData from '../evaluationMockData.json';

const evaluationHandlers = [
  http.get(EVALUATIONS, async () => HttpResponse.json(evaluationMockData)),

  http.post(EVALUATIONS, async ({ request }) => {
    const url = new URL(request.url);
    const processId = url.searchParams.get('processId');
    const applicantId = url.searchParams.get('applicantId');
    const body = (await request.json()) as {
      score: string;
      content: string;
    };

    if (!body.score || !body.content || !processId || !applicantId) {
      return new Response(null, {
        status: 404,
        statusText: 'Evaluation Not Found',
      });
    }

    return new Response(null, {
      status: 201,
      statusText: 'Created',
    });
  }),
];

export default evaluationHandlers;
