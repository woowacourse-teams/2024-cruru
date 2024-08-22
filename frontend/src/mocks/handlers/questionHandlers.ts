import { http, HttpResponse } from 'msw';

import { QUESTIONS } from '@api/endPoint';
import { ApplyForm } from '@customTypes/apply';
import { NotFoundError, Success } from './response';

const questionHandlers = [
  http.patch(QUESTIONS, async ({ request }) => {
    const url = new URL(request.url);
    const applyformId = url.searchParams.get('applyformId');
    const body = (await request.json()) as ApplyForm;

    if (!applyformId) {
      return NotFoundError();
    }

    if (!body.questions) {
      return new HttpResponse(null, {
        status: 400,
        statusText: 'questions not found',
      });
    }

    return Success();
  }),
];

export default questionHandlers;
