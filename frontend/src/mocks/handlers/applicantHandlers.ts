/* eslint-disable camelcase */
import { http, HttpResponse } from 'msw';

import { APPLICANTS } from '@api/endPoint';
import specificApplicant from '../specificApplicant.json';
import applicantDetail from '../applicantDetails.json';

const applicantHandlers = [
  http.put(`${APPLICANTS}/move-process/:processId`, async ({ request, params }) => {
    const { processId } = params;
    const body = await request.json();

    if (typeof body !== 'object' || !body?.applicantIds || body.applicantIds.length === 0) {
      throw new Error('body로 주어진 값이 {applicantIds: number[]} 형식이 아닙니다.');
    }

    body.applicantIds.forEach((id: string) => {
      if (!(typeof id === 'number')) {
        throw new Error('body로 주어진 값이 {applicantIds: number[]} 형식이 아닙니다.');
      }
    });

    const { applicantIds } = body;
    console.log(`${applicantIds}지원자(들)을/를 ${processId}에 해당하는 프로세스로 이동합니다.`);

    return HttpResponse.json({ status: 200 });
  }),

  http.get(`${APPLICANTS}/:applicantId`, () => HttpResponse.json(specificApplicant)),

  http.patch(`${APPLICANTS}/:applicantId/reject`, ({ params }) => {
    const { applicantId } = params;

    console.log(`${applicantId}지원자(들)을/를 불합격 처리 합니다.`);

    return HttpResponse.json({ status: 200 });
  }),

  http.patch(`${APPLICANTS}/:applicantId/unreject`, ({ params }) => {
    const { applicantId } = params;

    console.log(`${applicantId}지원자(들)을/를 불합격을 취소합니다.`);

    return HttpResponse.json({ status: 200 });
  }),

  http.get(`${APPLICANTS}/:applicantId/detail`, () => HttpResponse.json(applicantDetail)),
];

export default applicantHandlers;
