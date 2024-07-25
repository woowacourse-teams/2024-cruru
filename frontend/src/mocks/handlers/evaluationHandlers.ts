/* eslint-disable camelcase */
import { http, HttpResponse } from 'msw';

import { EVALUATIONS } from '@api/endPoint';
import evaluationMockData from '../evaluationMockData.json';

const evaluationHandlers = [
  http.get(EVALUATIONS, async () => HttpResponse.json(evaluationMockData)),

  http.post(EVALUATIONS, async ({ request }) => {
    const url = new URL(request.url);
    const processId = url.searchParams.get('process_id');
    const applicantId = url.searchParams.get('applicant_id');
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

    console.log(`해당 지원자에 대해 ${body.score}점과 ${body.content} 코멘트를 남겼습니다.`);

    return new Response(null, {
      status: 201,
      statusText: 'Created',
    });
  }),
];

export default evaluationHandlers;
