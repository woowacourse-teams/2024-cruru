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

    if (!body.email || !body.password || body.email !== 'admin@gmail.com' || body.password !== 'admin') {
      return new Response(null, {
        status: 401,
        statusText: '[Mock Data Error] Login Failed',
      });
    }

    const responseBody = JSON.stringify({
      clubId: 1,
    });

    return new Response(responseBody, {
      status: 201,
      statusText: 'Created',
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
