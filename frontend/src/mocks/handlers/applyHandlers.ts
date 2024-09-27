import { APPLY } from '@api/endPoint';
import { http, HttpResponse } from 'msw';
import applyForm from '../applyForm.json';
import { NotFoundError, Success } from './response';

const applyHandlers = [
  http.get(`${APPLY}/:applyFormId`, async ({ request }) => {
    const url = new URL(request.url);
    const applyFormId = url.pathname.split('/').pop();

    if (!applyFormId) {
      return NotFoundError();
    }

    return HttpResponse.json(applyForm);
  }),

  http.post(`${APPLY}/:applyFormId/submit`, async ({ request }) => {
    const url = new URL(request.url);
    const applyFormId = url.pathname.split('/').pop();

    if (!applyFormId) {
      return NotFoundError();
    }

    return Success();
  }),

  http.patch(`${APPLY}/:applyFormId`, async ({ request }) => {
    const url = new URL(request.url);
    const applyFormId = url.pathname.split('/').pop();

    if (!applyFormId) {
      return NotFoundError();
    }

    return Success();
  }),
];

export default applyHandlers;
