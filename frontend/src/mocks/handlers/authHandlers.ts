/* eslint-disable no-promise-executor-return */
import { AUTH } from '@api/endPoint';
import { http } from 'msw';
import { Success } from './response';

interface LoginFormData {
  email: string;
  password: string;
}

const authHandlers = [
  http.post(`${AUTH}/login`, async ({ request }) => {
    const body = (await request.json()) as LoginFormData;

    await new Promise((resolve) => setTimeout(resolve, 2000));

    if (!body.email || !body.password || body.email !== 'member@mail.com' || body.password !== 'qwer1234') {
      return new Response(JSON.stringify({ detail: '로그인 정보가 일치하지 않습니다.' }), {
        status: 401,
      });
    }

    const responseBody = JSON.stringify({
      clubId: 1,
    });

    return new Response(responseBody, {
      status: 201,
      headers: {
        'Content-Type': 'application/json',
      },
    });
  }),

  http.post(`${AUTH}/logout`, async () => {
    await new Promise((resolve) => setTimeout(resolve, 2000));
    return Success();
  }),
];

export default authHandlers;
