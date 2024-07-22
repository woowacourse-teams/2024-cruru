/* eslint-disable camelcase */
import { http, HttpResponse } from 'msw';

import { APPLICANTS } from '@api/endPoint';

const applicantHandlers = [
  http.put(`${APPLICANTS}/move-process/:processId`, async ({ request, params }) => {
    const { processId } = params;
    const body = await request.json();

    if (typeof body !== 'object' || !body?.applicant_ids || body.applicant_ids.length === 0) {
      throw new Error('body로 주어진 값이 {applicant_ids: number[]} 형식이 아닙니다.');
    }

    body.applicant_ids.forEach((id: string) => {
      if (!(typeof id === 'number')) {
        throw new Error('body로 주어진 값이 {applicant_ids: number[]} 형식이 아닙니다.');
      }
    });

    const { applicant_ids } = body;
    console.log(`${applicant_ids}지원자(들을)를 ${processId}에 해당하는 프로세스로 이동합니다.`);

    return HttpResponse.json({ status: 200 });
  }),
];

export default applicantHandlers;
