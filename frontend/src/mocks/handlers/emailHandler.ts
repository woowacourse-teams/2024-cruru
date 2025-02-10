/* eslint-disable no-promise-executor-return */
import { http, HttpResponse } from 'msw';
import { EMAILS } from '@api/endPoint';
import emailHistory from '../emailHistory.json';

const emailHandlers = [
  http.post(`${EMAILS}/send`, async ({ request }) => {
    const body = (await request.formData()) as FormData;

    await new Promise((resolve) => setTimeout(resolve, 2000));

    if (!body.get('subject') || !body.get('content')) {
      return new Response(null, {
        status: 400,
      });
    }

    return new Response(null, {
      status: 200,
    });
  }),

  http.post(`${EMAILS}/verification-code`, async ({ request }) => {
    const { email } = (await request.json()) as { email: string };

    await new Promise((resolve) => setTimeout(resolve, 2000));

    if (!email) {
      return new Response(
        JSON.stringify({
          detail: '이메일 형식을 확인해주세요.',
        }),
        {
          status: 400,
        },
      );
    }

    return new Response(null, {
      status: 200,
    });
  }),

  http.post(`${EMAILS}/verify-code`, async ({ request }) => {
    const { email, verificationCode } = (await request.json()) as { email: string; verificationCode: string };

    await new Promise((resolve) => setTimeout(resolve, 2000));

    if (!email || !verificationCode) {
      return new Response(null, {
        status: 400,
      });
    }

    if (verificationCode !== '123456') {
      return new Response(
        JSON.stringify({
          detail: '인증 코드가 존재하지 않거나 만료되었습니다.',
        }),
        {
          status: 400,
        },
      );
    }

    return new Response(null, {
      status: 200,
    });
  }),

  http.get(`${EMAILS}/:clubId/:applicantId`, ({ params }) => {
    const { clubId, applicantId } = params;

    if (!clubId || !applicantId) {
      return new Response(
        JSON.stringify({
          message: `필수 입력 정보가 누락됨: ${!clubId && 'clubId'} ${!applicantId && 'applicantId'}`,
        }),
      );
    }

    return HttpResponse.json({ emailHistoryResponses: emailHistory });
  }),
];

export default emailHandlers;
