/* eslint-disable no-promise-executor-return */
import { http } from 'msw';
import { EMAILS } from '@api/endPoint';

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
];

export default emailHandlers;
